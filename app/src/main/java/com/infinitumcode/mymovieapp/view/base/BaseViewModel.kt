package com.infinitumcode.mymovieapp.view.base

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.infinitumcode.mymovieapp.util.SingleLiveEvent
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {

    protected val job = SupervisorJob()
    protected val main: CoroutineDispatcher = Dispatchers.Main
    protected val io: CoroutineDispatcher = Dispatchers.IO
    protected val default: CoroutineDispatcher = Dispatchers.Default

    protected var pagedConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(10)
        .setInitialLoadSizeHint(10)
        .setEnablePlaceholders(false)
        .build()

    protected val progressState: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun onCleared() {
        job.cancelChildren()
        super.onCleared()
    }
}