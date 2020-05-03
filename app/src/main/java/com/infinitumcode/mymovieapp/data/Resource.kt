package com.infinitumcode.mymovieapp.data

data class Resource<out T> (
        val status: Status,
        val data: T?,
        val error: Throwable?
)