package com.ntechnology.ddinfo.extensions

import android.os.Parcel

fun Parcel.readStringOrEmpty(): String {
    return readString().orEmpty()
}

fun Parcel.readBoolean(): Boolean {
    return readByte() == 1.toByte()
}

fun Parcel.writeBoolean(value: Boolean) {
    writeByte(if (value) 1 else 0)
}

inline fun <reified T> Parcel.readCollection(): List<T> {
    return arrayListOf<T>().apply {
        readList(this, T::class.java.classLoader)
    }
}

fun Parcel.createAndReadStringList(): List<String> {
    return arrayListOf<String>().apply {
        readStringList(this)
    }
}