package com.infinitumcode.mymovieapp.data.remote

import com.infinitumcode.mymovieapp.domain.pojo.MovieDetail
import com.infinitumcode.mymovieapp.domain.pojo.MovieQuery
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {

    @GET("discover/movie")
    suspend fun popularMovie(@QueryMap params: Map<String, String>): Response<MovieQuery>

    @GET("trending/movie/{time_window}")
    suspend fun trendingMovie(@Path("time_window") time: String, @QueryMap params: Map<String, String>): Response<MovieQuery>

    @GET("search/movie")
    suspend fun searchMovie(@QueryMap params: Map<String, String>): Response<MovieQuery>

    @GET("movie/{movie_id}")
    suspend fun movieDetail(@Path("movie_id") movieId: Long, @Query("append_to_response") value: String): Response<MovieDetail>

}