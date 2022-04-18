package com.ntechnology.ddinfo.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ntechnology.ddinfo.models.*
import com.squareup.moshi.Moshi

class GradeStorage(
    private val sharedPreferences: SharedPreferences, private val moshi: Moshi) {

    fun saveGradeState(gradesModel: GradesModel, gradeState: MutableMap<String, MutableList<String>>) {

        val rr = GradeStateModel(gradeState)


        val json = moshi.adapter(GradeStateModel::class.java).toJson(rr)
        sharedPreferences.edit {
            putString(gradesModel.id, json)
        }
    }

    fun getGradeState(gradesModel: GradesModel): MutableMap<String, MutableList<String>>? {
        return try {
            val json = sharedPreferences.getString(gradesModel.id, "").orEmpty()
            moshi.adapter(GradeStateModel::class.java).fromJson(json)?.gradeState
        } catch (ex: Exception) {
            null
        }
    }

    fun saveGradeState(gradesModel: GenderGradesModel, gradeState: MutableMap<Int, SelectedGenderGrade>) {
        val rr = GenderGradeStateModel(gradeState)


        val json = moshi.adapter(GenderGradeStateModel::class.java).toJson(rr)
        sharedPreferences.edit {
            putString(gradesModel.id, json)
        }
    }

    fun getGenderGradeState(grades: GenderGradesModel): MutableMap<Int, SelectedGenderGrade>? {
        return try {
            val json = sharedPreferences.getString(grades.id, "").orEmpty()
            moshi.adapter(GenderGradeStateModel::class.java).fromJson(json)?.gradeState
        } catch (ex: Exception) {
            null
        }
    }
}