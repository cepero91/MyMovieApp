package com.infinitumcode.mymovieapp.view.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import com.infinitumcode.mymovieapp.domain.pagination.MovieDataSourceFactory
import com.infinitumcode.mymovieapp.domain.pagination.PaginationState
import com.infinitumcode.mymovieapp.domain.repository.MovieRepository
import com.infinitumcode.mymovieapp.util.QueryHelper
import com.infinitumcode.mymovieapp.view.base.BaseViewModel
import javax.inject.Inject

class PopularViewModel @Inject constructor(repository: MovieRepository) : BaseViewModel()  {

    private var movieDataSourceFactory: MovieDataSourceFactory =
        MovieDataSourceFactory(
            MovieRepository.QUERYTAG.DISCOVER,
            QueryHelper.popularMovies(),
            repository
        )

    val moviePagedLiveData = pagedConfig.let {
        LivePagedListBuilder(movieDataSourceFactory,
            it
        ).build()
    }

    val paginationState: LiveData<PaginationState>? =
        Transformations.switchMap(movieDataSourceFactory.movieDataSourceLiveData) { it.getPaginationState() }

    /**
     * Retry possible last paged request (ie: network issue)
     */
    fun refreshFailedRequest() =
        movieDataSourceFactory.getSource()?.retryFailedQuery()

    /**
     * Refreshes all list after an issue
     */
    fun refreshAllList() =
        movieDataSourceFactory.getSource()?.refresh()
}
