package com.ntechnology.ddinfo.utils

import android.text.TextUtils
import com.ntechnology.ddinfo.models.GenderGradesModel
import com.ntechnology.ddinfo.models.GradeResultModel
import com.ntechnology.ddinfo.models.GradesModel
import com.ntechnology.ddinfo.models.SelectedGenderGrade

class GradesProvider {
    fun getGrade(
        gradesModel: GradesModel, selectionMap: MutableMap<String, MutableList<String>>): ArrayList<GradeResultModel>? {

        val keys = selectionMap.keys

        var result = gradesModel.results

        for (key in keys)

            result = result.filter {

                if (selectionMap[key]!!.isNotEmpty()) {
                    for (value in it) {
                        if (value == selectionMap[key]!![0]) return@filter true
                    }
                }
                return@filter false
            }

        if (result.isEmpty()) return null
        if (result.size > 1) return null
        val resultArray = ArrayList<GradeResultModel>()

        for (i in 0 until gradesModel.resultTitles.size) resultArray.add(
            GradeResultModel(
                gradesModel.resultTitles[i], result[0][i + 3]))


        return resultArray
    }

    fun getGenderGrade(
        genderGradesModel: GenderGradesModel,
        selectionMap: MutableMap<Int, SelectedGenderGrade>): MutableMap<Int, Int> {
        val result = mutableMapOf<Int, Int>()

        var total = 0

        for (i in 1..5) {
            selectionMap[i]?.let {
                if (!TextUtils.isEmpty(it.title) && !TextUtils.isEmpty(it.value)) {
                    for (grade in genderGradesModel.items) {
                        if (grade.title == it.title) {
                            for (value in grade.value) {
                                if (value[1] == it.value) {
                                    result[i] = value[0].toInt()
                                    total += value[0].toInt()
                                    return@let
                                }
                            }
                        }
                    }
                }
            }
        }
        if (total != 0) result[6] = total

        return result
    }
}