package com.dicoding.asclepius.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.local.entity.Analysis
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.viewmodel.MainViewModel
import com.dicoding.asclepius.viewmodel.ViewModelFactory
import org.tensorflow.lite.support.label.Category
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(READ_MEDIA_IMAGES)
        } else {
            requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
        }

        binding.progressIndicator.visibility = View.VISIBLE
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResults(results: List<Classifications>?) {
                    runOnUiThread {
                        results?.let { it ->
                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                val result = it[0].categories[0]
                                moveToResult(result)
                            } else {

                            }
                        }
                    }
                }
            }
        )
        binding.progressIndicator.visibility = View.GONE

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(application = application)
        )[MainViewModel::class.java]

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener { analyzeImage() }
        binding.historyButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        binding.newsButton.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
            showToast("No media selected")
        }
    }

    private fun saveImageToInternalStorage(uri: Uri): String? {
        val contentResolver = contentResolver
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val destinationPath = getFilesDir().toString() + "/" + fileName

        try {
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val outputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            val buffer = ByteArray(1024)
            var readBytes: Int
            while (inputStream.read(buffer).also { readBytes = it } != -1) {
                outputStream.write(buffer, 0, readBytes)
            }
            inputStream.close()
            outputStream.close()
            return destinationPath
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        currentImageUri?.let {
            binding.progressIndicator.visibility = View.VISIBLE
            imageClassifierHelper.classifyStaticImage(it)
            binding.progressIndicator.visibility = View.GONE
        } ?: showToast("Please select a photo first")
    }

    private fun moveToResult(result: Category) {
        currentImageUri?.let { imageUri ->
            val imagePath = saveImageToInternalStorage(imageUri)
            if (imagePath != null) {
                viewModel.insertAnalysis(
                    Analysis(
                        imageUri = imagePath,
                        label = result.label,
                        score = result.score
                    )
                )

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_IMAGE, imageUri.toString())
                intent.putExtra(ResultActivity.EXTRA_LABEL, result.label)
                intent.putExtra(ResultActivity.EXTRA_SCORE, result.score)

                startActivity(intent)
            } else {
                showToast("Failed to save image")
                Log.e(TAG, "Error saving image to internal storage")
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
