package com.example.apodapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ApodEntity::class], version = 1, exportSchema = false)
abstract class ApodDatabase : RoomDatabase() {
    abstract fun apodDao(): ApodDao

    companion object {
        private var INSTANCE: ApodDatabase? = null

        fun getDatabase(context: Context): ApodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApodDatabase::class.java,
                    "apod_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
