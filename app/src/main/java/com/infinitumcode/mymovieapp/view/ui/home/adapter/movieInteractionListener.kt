package com.infinitumcode.mymovieapp.view.ui.home.adapter

import com.infinitumcode.mymovieapp.domain.pojo.MovieResult

interface movieInteractionListener {
    fun onClickRetry()
    fun onMovieClick(movieResult: MovieResult, pos: Int)
}