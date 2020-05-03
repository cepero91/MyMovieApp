package com.infinitumcode.mymovieapp.view.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.infinitumcode.mymovieapp.R
import com.infinitumcode.mymovieapp.domain.pojo.MovieResult
import com.infinitumcode.mymovieapp.view.ui.home.adapter.movieInteractionListener
import com.infinitumcode.mymovieapp.view.ui.home.viewholder.MovieViewHolder

class FavoriteListAdapter(private val listener: movieInteractionListener) : RecyclerView.Adapter<MovieViewHolder>() {

    private var movies: List<MovieResult> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_view_layout, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, listener)
    }

    fun submitList(submitMovies: List<MovieResult>) {
        this.movies = submitMovies
        notifyDataSetChanged()
    }
}