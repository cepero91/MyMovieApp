package com.infinitumcode.mymovieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.infinitumcode.mymovieapp.domain.pojo.MovieResult

@Database(entities = [(MovieResult::class)], version = 1, exportSchema = false)
abstract class MovieLocalStorage : RoomDatabase() {
    abstract fun movieDao(): Dao
}