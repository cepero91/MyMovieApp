package com.infinitumcode.mymovieapp.view.ui.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.infinitumcode.mymovieapp.R
import com.infinitumcode.mymovieapp.domain.pojo.MovieResult
import com.infinitumcode.mymovieapp.view.ui.home.adapter.movieInteractionListener
import com.infinitumcode.mymovieapp.util.Constants.Companion.BASE_IMAGE_URL_API
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_view_layout.view.*

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        movieResult: MovieResult?,
        listener: movieInteractionListener
    ) {
        if (movieResult != null) {
            itemView.tv_movie_title.text = movieResult.title
            var url = BASE_IMAGE_URL_API
            url += if (!movieResult.backdrop_path.isNullOrEmpty()) {
                movieResult.backdrop_path
            } else movieResult.poster_path
            if (movieResult.title.length > 30)
                itemView.tv_movie_title.isSelected = true
            itemView.tvVoteAverage.text = movieResult.vote_average.toString()
            Picasso.get().load(url).error(R.drawable.small_placeholder).into(itemView.iv_movie)
            itemView.setOnClickListener { listener.onMovieClick(movieResult, adapterPosition) }
        }
    }

}