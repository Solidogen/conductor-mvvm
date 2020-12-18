package com.example.conductormvvm.util.extensions

import android.content.SharedPreferences
import androidx.core.content.edit

fun SharedPreferences.commitBoolean(key: String, value: Boolean) = edit(commit = true) { putBoolean(key, value) }
fun SharedPreferences.commitString(key: String, value: String?) = edit(commit = true) { putString(key, value) }
fun SharedPreferences.commitStringSet(key: String, value: Set<String>) = edit(commit = true) { putStringSet(key, value) }
fun SharedPreferences.commitInt(key: String, value: Int) = edit(commit = true) { putInt(key, value) }
fun SharedPreferences.commitLong(key: String, value: Long) = edit(commit = true) { putLong(key, value) }