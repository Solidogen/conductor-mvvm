package com.example.conductormvvm.utils

/**
 * Koin injects objects lazily, if test does not touch object at all, it needs to be force initialized
 * */
fun <T> T.forceInject() = this