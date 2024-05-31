package com.dicoding.asclepius.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.local.entity.Analysis
import com.dicoding.asclepius.data.local.room.AnalysisDao
import com.dicoding.asclepius.data.local.room.AnalysisHistoryDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainViewModel(application: Application) : ViewModel() {
    private val analysisDao: AnalysisDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = AnalysisHistoryDatabase.getInstance(application)
        analysisDao = db.analysisDao()
    }

    fun insertAnalysis(analysis: Analysis) =
        executorService.execute { analysisDao.insertAnalysis(analysis) }
}