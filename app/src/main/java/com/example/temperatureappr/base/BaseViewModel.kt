package com.example.temperatureappr.base

import androidx.lifecycle.ViewModel
import com.example.temperatureappr.data.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main
    val showError = SingleLiveEvent<String>()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}