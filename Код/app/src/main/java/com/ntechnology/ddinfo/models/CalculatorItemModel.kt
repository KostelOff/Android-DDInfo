package com.ntechnology.ddinfo.models

import android.os.Parcel
import com.ntechnology.ddinfo.extensions.*

data class CalculatorItemModel(
    val id: String,
    val name: String,
    val description: String,
    val type: String,
    val isOptional: Boolean,
    val floatAllows: Boolean?,
    val maxSelections: Int,
    val values: List<SelectionModel>,
    val inputTypes: List<String>,
    val maxInputValue: Int?) : KParcelable {

    constructor(parcel: Parcel) : this(
        parcel.readStringOrEmpty(),
        parcel.readStringOrEmpty(),
        parcel.readStringOrEmpty(),
        parcel.readStringOrEmpty(),
        parcel.readBoolean(),
        parcel.readBoolean(),
        parcel.readInt(),
        parcel.readCollection(),
        parcel.createAndReadStringList(),
        parcel.readInt())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(description)
        writeString(type)
        writeBoolean(isOptional)
        writeBoolean(floatAllows ?: false)
        writeInt(maxSelections)
        writeTypedList(values)
        writeStringList(inputTypes)
        writeInt(maxInputValue ?: 0)
    }

    companion object {
        const val TYPE_SELECTION = "selection"
        const val TYPE_INPUT = "input"

        @JvmField
        val CREATOR = parcelableCreator(::SelectionModel)
    }
}