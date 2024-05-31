package com.dicoding.asclepius.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "analysis")
data class Analysis(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @field:ColumnInfo(name = "image_uri")
    val imageUri: String,

    @field:ColumnInfo(name = "label")
    val label: String,

    @field:ColumnInfo(name = "score")
    val score: Float,
)