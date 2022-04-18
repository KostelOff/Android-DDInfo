package com.ntechnology.ddinfo.utils

import android.content.res.AssetManager
import com.ntechnology.ddinfo.extensions.safetyRead
import com.ntechnology.ddinfo.models.GradesModel
import com.ntechnology.ddinfo.models.PhysicalMenuItemModel
import com.squareup.moshi.Moshi

class GradeItemsProvider(
    private val assetManager: AssetManager, private val moshi: Moshi) {

    fun readGrade(type: String): GradesModel? {
        return readKeyboard(
            when (type) {
                PhysicalMenuItemModel.ID_PHYSICAL_GRADES -> PHYSICAL_GRADES_JSON
                else -> null
            })
    }

    private fun readKeyboard(path: String?): GradesModel? {
        if (path != null) {
            return assetManager.safetyRead(path)?.let { json ->
                moshi.adapter(GradesModel::class.java).fromJson(json)
            }
        }
        return null
    }

    companion object {
        private const val PHYSICAL_GRADES_JSON = "ddinfo_physical_grades.json"
    }
}