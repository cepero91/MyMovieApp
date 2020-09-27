package com.infinitumcode.mymovieapp.domain.pagination

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PageKeyedDataSource
import com.infinitumcode.mymovieapp.domain.pojo.MovieResult
import com.infinitumcode.mymovieapp.domain.repository.MovieRepository
import com.infinitumcode.mymovieapp.util.SingleLiveEvent
import kotlinx.coroutines.*
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class MovieDataSource(
    private val queryTag: MovieRepository.QUERYTAG,
    private val queryParams: HashMap<String, String>,
    private val repository: MovieRepository
) : PageKeyedDataSource<Int, MovieResult>() {

    var state: SingleLiveEvent<PaginationState> = SingleLiveEvent()
    private var job = SupervisorJob()
    private val io: CoroutineContext = Dispatchers.IO
    private val scope = CoroutineScope(getJobErrorHandler() + io + job)
    private var retryQuery: (() -> Any)? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieResult>
    ) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery(1, queryTag) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResult>) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeQuery(page, queryTag ) {
            callback.onResult(it, page.plus(1))
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResult>) {
        //do nothing
    }

    private fun executeQuery(
        page: Int,
        queryTag: MovieRepository.QUERYTAG,
        callback: (List<MovieResult>) -> Unit
    ) {
        updateState(PaginationState.LOADING)
        scope.launch {
            queryParams["page"] = page.toString()
            val result = repository.fetchMovies(queryParams, queryTag)
            retryQuery = null
            val listing = result.body()
            if (listing?.results != null && listing.results.isNotEmpty()) {
                updateState(PaginationState.DONE)
            } else updateState(PaginationState.EMPTY)
            callback(listing?.results ?: listOf())
        }
    }


    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Log.e(MovieDataSource::class.java.simpleName, "An error happened: $e")
        updateState(PaginationState.ERROR)
    }

    private fun updateState(pState: PaginationState) {
        this.state.postValue(pState)
    }

    fun refresh() =
        this.invalidate()

    override fun invalidate() {
        job.cancelChildren()
        super.invalidate()
    }

    fun retryFailedQuery() {
        val prevQuery = retryQuery
        retryQuery = null
        prevQuery?.invoke()
    }

    fun getPaginationState(): LiveData<PaginationState> =
        state

}