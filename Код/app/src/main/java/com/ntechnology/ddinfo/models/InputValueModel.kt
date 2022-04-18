package com.ntechnology.ddinfo.models

import android.os.Parcel
import com.ntechnology.ddinfo.extensions.readStringOrEmpty
import java.util.*
import kotlin.math.truncate

data class InputValueModel(val value: Float, val type: String) : KParcelable {
    constructor(parcel: Parcel) : this(parcel.readFloat(), parcel.readStringOrEmpty())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeFloat(value)
        writeString(type)
    }

    fun getTitle(): String {
        return when (type) {
            INPUT_TYPE_PERCENT -> "${getTruncatedString()} %"
            INPUT_TYPE_VALUE -> "${getTruncatedString()} руб."
            INPUT_TYPE_QUANTITY -> getTruncatedString()
            else -> getTruncatedString()
        }
    }

    fun getTruncatedString(): String {
        return if (value == truncate(value)) {
            value.toInt().toString()
        } else {
            "%.2f".format(Locale.US, value)
        }
    }

    companion object {
        const val INPUT_TYPE_PERCENT = "percent"
        const val INPUT_TYPE_VALUE = "value"
        const val INPUT_TYPE_QUANTITY = "quantity"

        @JvmField
        val CREATOR = parcelableCreator(::InputValueModel)
    }
}