package com.example.apodapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ApodDao {
    @Query("SELECT * FROM apod WHERE date = :date")
    fun getApod(date: String): ApodEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertApod(apodEntity: ApodEntity): Long
}
