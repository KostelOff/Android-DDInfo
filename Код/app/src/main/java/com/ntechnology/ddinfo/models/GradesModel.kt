package com.ntechnology.ddinfo.models

import android.os.Parcel
import com.ntechnology.ddinfo.extensions.readStringOrEmpty

data class GradesModel(
    val id: String,
    val name: String,
    val items: List<GradesSelectionItem>,
    val results: List<List<String>>,
    val resultTitles: List<String>)


data class GradesSelectionItem(val title: String, val description: String?, var values: List<String> = emptyList()) :
    KParcelable {

    constructor(parcel: Parcel) : this(parcel.readStringOrEmpty(), parcel.readStringOrEmpty()) {

        val arrayList = ArrayList<String>()

        parcel.readStringList(arrayList)
        this.values = arrayList
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(description)
        writeStringList(values)
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::GradesSelectionItem)
    }
}

data class GradesSelectionViewItem(val item: GradesSelectionItem, val selections: List<String>)