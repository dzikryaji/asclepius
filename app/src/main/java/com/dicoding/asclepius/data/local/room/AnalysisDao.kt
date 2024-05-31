package com.dicoding.asclepius.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.Analysis

@Dao
interface AnalysisDao {

    @Query("SELECT * FROM analysis")
    fun getAnalyses(): List<Analysis>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAnalysis(analysis: Analysis)

}