package com.infinitumcode.mymovieapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.infinitumcode.mymovieapp.domain.pojo.MovieResult

@Dao
interface Dao {

    @Query("SELECT * FROM MovieLocal")
    fun allFavorite(): LiveData<List<MovieResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movieLocal: MovieResult)

    @Update
    suspend fun updateFavorite(movieLocal: MovieResult)

    @Delete
    suspend fun deleteFavorite(movieLocal: MovieResult)

    @Query("SELECT * FROM MovieLocal WHERE id=:id LIMIT 1")
    fun existAsFavorite(id: String): LiveData<List<MovieResult>>

}