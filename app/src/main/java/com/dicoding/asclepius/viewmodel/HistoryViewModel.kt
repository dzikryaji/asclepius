package com.dicoding.asclepius.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.local.entity.Analysis
import com.dicoding.asclepius.data.local.room.AnalysisDao
import com.dicoding.asclepius.data.local.room.AnalysisHistoryDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryViewModel(application: Application) : ViewModel() {
    private val analysisDao: AnalysisDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    private val _analyses = MutableLiveData<List<Analysis>>()
    val analyses: LiveData<List<Analysis>> = _analyses

    init {
        val db = AnalysisHistoryDatabase.getInstance(application)
        analysisDao = db.analysisDao()
        getAnalyses()
    }

    private fun getAnalyses() {
        executorService.execute {
            _analyses.postValue(analysisDao.getAnalyses())
        }
    }

}