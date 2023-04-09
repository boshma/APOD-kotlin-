package com.example.apodapp

data class ApodResponse(
    val date: String,
    val explanation: String,
    val hdurl: String?,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String,
    val copyright: String?
)
