package com.dicoding.asclepius.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(
    private val application: Application? = null
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            if (application != null) {
                return MainViewModel(application) as T
            }
            throw IllegalArgumentException("Application parameter for ${modelClass.name} is null")
        } else if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            if (application != null) {
                return HistoryViewModel(application) as T
            }
            throw IllegalArgumentException("Application parameter for ${modelClass.name} is null")
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}