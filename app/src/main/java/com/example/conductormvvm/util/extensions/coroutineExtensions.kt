package com.example.conductormvvm.util.extensions

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

/**
 * Those 3 are okay to use with StateFlow. SharedFlow may miss emits if we are starting too early.
 * Even
 * */
fun <T> Flow<T>.launchWhenCreated(scope: LifecycleCoroutineScope): Job =
    scope.launchWhenCreated { collect() }

fun <T> Flow<T>.launchWhenStarted(scope: LifecycleCoroutineScope): Job =
    scope.launchWhenStarted { collect() }

fun <T> Flow<T>.launchWhenResumed(scope: LifecycleCoroutineScope): Job =
    scope.launchWhenResumed { collect() }

