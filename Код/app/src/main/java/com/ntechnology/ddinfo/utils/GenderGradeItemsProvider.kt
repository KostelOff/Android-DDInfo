package com.ntechnology.ddinfo.utils

import android.content.res.AssetManager
import com.ntechnology.ddinfo.extensions.safetyRead
import com.ntechnology.ddinfo.models.GenderGradesModel
import com.ntechnology.ddinfo.models.GradesModel
import com.ntechnology.ddinfo.models.PhysicalMenuItemModel
import com.squareup.moshi.Moshi

class GenderGradeItemsProvider(
    private val assetManager: AssetManager, private val moshi: Moshi) {

    fun readGrade(type: String): GenderGradesModel? {
        return readKeyboard(
            when (type) {
                PhysicalMenuItemModel.ID_MALE_PHYSICAL -> PHYSICAL_MALE_GRADES_JSON
                PhysicalMenuItemModel.ID_FEMALE_PHYSICAL -> PHYSICAL_FEMALE_GRADES_JSON
                else -> null
            })
    }

    private fun readKeyboard(path: String?): GenderGradesModel? {
        if (path != null) {
            return assetManager.safetyRead(path)?.let { json ->
                moshi.adapter(GenderGradesModel::class.java).fromJson(json)
            }
        }
        return null
    }

    companion object {
        private const val PHYSICAL_MALE_GRADES_JSON = "ddinfo_male_physical_grades.json"
        private const val PHYSICAL_FEMALE_GRADES_JSON = "ddinfo_female_physical_grades.json"
    }
}