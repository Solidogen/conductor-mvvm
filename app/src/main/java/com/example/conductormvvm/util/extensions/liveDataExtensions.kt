package com.example.conductormvvm.util.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.conductormvvm.util.utils.Event
import com.example.conductormvvm.util.utils.EventObserver

fun <T> MutableLiveData<Event<T>>.emitEvent(item: T) {
    value = Event(item)
}

fun <T> MutableLiveData<Event<T>>.postEvent(item: T) {
    postValue(Event(item))
}

fun <T> LiveData<Event<T>>.observeEvents(
    lifecycleOwner: LifecycleOwner,
    onEventUnhandledContent: (T) -> Unit
) {
    observe(lifecycleOwner, EventObserver {
        onEventUnhandledContent.invoke(it)
    })
}