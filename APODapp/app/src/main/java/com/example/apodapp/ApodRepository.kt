package com.example.apodapp

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class ApodRepository(private val context: Context) {
    private val apodDao = ApodDatabase.getDatabase(context).apodDao()
    private val apodApiService = ApodApiService.create()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    suspend fun getApod(date: String? = null): ApodEntity? {
        val currentDate = date ?: dateFormat.format(Date())
        var apodEntity = apodDao.getApod(currentDate)

        if (apodEntity == null) {
            val apodResponse = apodApiService.getApod(currentDate)
            apodEntity = ApodEntity(
                date = apodResponse.date,
                explanation = apodResponse.explanation,
                hdurl = apodResponse.hdurl,
                media_type = apodResponse.media_type,
                title = apodResponse.title,
                url = apodResponse.url,
                copyright = apodResponse.copyright
            )
            apodDao.insertApod(apodEntity)
        }

        return apodEntity
    }
}
