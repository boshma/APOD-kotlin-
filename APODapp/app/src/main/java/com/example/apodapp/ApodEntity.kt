package com.example.apodapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apod")
data class ApodEntity(
    @PrimaryKey
    val date: String,
    val explanation: String,
    val hdurl: String?,
    val media_type: String,
    val title: String,
    val url: String,
    val copyright: String?
)
