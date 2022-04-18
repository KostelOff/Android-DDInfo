package com.ntechnology.ddinfo.models

import android.os.Parcel
import com.ntechnology.ddinfo.extensions.readStringOrEmpty

data class SelectionModel(val title: String, val value: Float) : KParcelable {
    constructor(parcel: Parcel) : this(parcel.readStringOrEmpty(), parcel.readFloat())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeFloat(value)
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::SelectionModel)
    }
}