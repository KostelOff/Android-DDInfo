package com.ntechnology.ddinfo.extensions

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import timber.log.Timber
import java.io.IOException
import java.io.InputStream

fun Context.getCompatColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.getDefaultSharedPreferences(): SharedPreferences =
    getSharedPreferences("defaultSharePreferences", Context.MODE_PRIVATE)

fun AssetManager.safetyRead(path: String): String? {
    return try {
        open(path).safetyRead()
    } catch (ioe: IOException) {
        Timber.e("safetyRead with exception: $ioe")
        null
    }
}

fun InputStream.safetyRead(): String? {
    return try {
        bufferedReader().use { it.readText() }
    } catch (ioe: IOException) {
        Timber.e("safetyRead with exception: $ioe")
        null
    }
}