package com.infinitumcode.mymovieapp.view.ui.popular

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.infinitumcode.mymovieapp.R
import com.infinitumcode.mymovieapp.domain.pagination.PaginationState
import com.infinitumcode.mymovieapp.domain.pojo.MovieResult
import com.infinitumcode.mymovieapp.view.customview.EmptyView
import com.infinitumcode.mymovieapp.view.ui.home.adapter.MoviePagedListAdapter
import com.infinitumcode.mymovieapp.view.ui.home.adapter.movieInteractionListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.popular_fragment.*
import javax.inject.Inject

class PopularFragment : Fragment(R.layout.popular_fragment), SwipeRefreshLayout.OnRefreshListener,
    movieInteractionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var popularViewModel: PopularViewModel
    private lateinit var pagedAdapter: MoviePagedListAdapter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularViewModel =
            ViewModelProvider(this, viewModelFactory).get(PopularViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
        obsStates()
        obsData()
    }

    private fun obsStates() {
        popularViewModel.paginationState?.observe(viewLifecycleOwner, Observer {
            updateUIPaginationState(it)
            pagedAdapter.updatePaginationState(it)
        })
    }

    private fun initUI() {
        emptyView.emptyStateType(EmptyView.STATETYPE.NOERROR, null)
        swipe.setOnRefreshListener(this)
        pagedAdapter = MoviePagedListAdapter(this)
        popularList.layoutManager = gridLayoutManager()
        popularList.adapter = pagedAdapter
    }


    private fun obsData() {
        popularViewModel.moviePagedLiveData.observe(viewLifecycleOwner, Observer { pagedList ->
            pagedAdapter.submitList(pagedList)
        })
    }

    private fun gridLayoutManager(): RecyclerView.LayoutManager? {
        val mLayoutManager = GridLayoutManager(activity, 2)
        mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (pagedAdapter.getItemViewType(position)) {
                    R.layout.loading_view_layout -> mLayoutManager.spanCount
                    else -> 1
                }
            }
        }
        return mLayoutManager
    }

    override fun onClickRetry() {
        popularViewModel.refreshFailedRequest()
    }

    override fun onMovieClick(movieResult: MovieResult, pos: Int) {
        val bundle = bundleOf("movie" to movieResult)
        findNavController().navigate(R.id.detail_fragment, bundle)
    }

    private fun updateUIPaginationState(paginationState: PaginationState?) {
        when (paginationState) {
            PaginationState.LOADING -> {
                swipe.isRefreshing = true
            }
            PaginationState.EMPTY -> {
                swipe.isRefreshing = false
                if (pagedAdapter.currentList.isNullOrEmpty()) {
                    emptyView.emptyStateType(EmptyView.STATETYPE.EMPTY, null)
                }
            }
            PaginationState.ERROR -> {
                swipe.isRefreshing = false
                if (pagedAdapter.currentList.isNullOrEmpty()) {
                    emptyView.emptyStateType(EmptyView.STATETYPE.CONNECTION, View.OnClickListener {
                        onRefresh()
                    })
                }
            }
            PaginationState.DONE -> {
                swipe.isRefreshing = false
                emptyView.emptyStateType(EmptyView.STATETYPE.NOERROR, null)
            }
        }
    }

    override fun onRefresh() {
        popularViewModel.refreshAllList()
    }



}
