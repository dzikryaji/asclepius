package com.dicoding.asclepius.view

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import java.text.DecimalFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE))
        val label = intent.getStringExtra(EXTRA_LABEL)
        val score = intent.getFloatExtra(EXTRA_SCORE, 0F)

        val scoreInPercent = DecimalFormat("##%").format(score)

        binding.resultImage.setImageURI(imageUri)
        binding.resultText.text = "$label $scoreInPercent"
        if (label == "Cancer"){
            binding.resultText.setTextColor(getColor(R.color.red))
        } else {
            binding.resultText.setTextColor(getColor(R.color.green))
        }
    }

    companion object {
        private const val TAG = "ResultActivity"
        const val EXTRA_IMAGE = "EXTRA_IMAGE"
        const val EXTRA_LABEL = "EXTRA_LABEL"
        const val EXTRA_SCORE = "EXTRA_SCORE"
    }


}