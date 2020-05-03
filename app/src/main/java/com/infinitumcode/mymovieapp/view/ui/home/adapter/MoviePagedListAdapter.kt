package com.infinitumcode.mymovieapp.view.ui.home.adapter

import android.view.LayoutInflater
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.infinitumcode.mymovieapp.R
import com.infinitumcode.mymovieapp.domain.pagination.PaginationState
import com.infinitumcode.mymovieapp.domain.pojo.MovieResult
import com.infinitumcode.mymovieapp.view.ui.home.viewholder.LoadingViewHolder
import com.infinitumcode.mymovieapp.view.ui.home.viewholder.MovieViewHolder

class MoviePagedListAdapter(private val listener: movieInteractionListener) :
    PagedListAdapter<MovieResult, RecyclerView.ViewHolder>(diffUtilCallback) {

    private var state: PaginationState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.movie_view_layout -> MovieViewHolder(view)
            R.layout.loading_view_layout -> LoadingViewHolder(view)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.movie_view_layout -> (holder as MovieViewHolder).bind(
                getItem(position),
                listener
            )
            R.layout.loading_view_layout -> (holder as LoadingViewHolder).bind(state, listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) {
            R.layout.movie_view_layout
        } else {
            R.layout.loading_view_layout
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == PaginationState.LOADING || state == PaginationState.ERROR)
    }

    fun updatePaginationState(newState: PaginationState) {
        this.state = newState
        if(newState != PaginationState.LOADING){
            notifyDataSetChanged()
        }
    }

    companion object {
        private val diffUtilCallback = object : DiffUtil.ItemCallback<MovieResult>() {
            override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
                return oldItem == newItem
            }
        }
    }


}