package com.ntechnology.ddinfo.utils

import com.ntechnology.ddinfo.models.GradeResultModel
import com.ntechnology.ddinfo.models.GradesModel

class GenderGradesProvider {
    fun getGrade(
        gradesModel: GradesModel, selectionMap: MutableMap<String, MutableList<String>>): ArrayList<GradeResultModel>? {

        val keys = selectionMap.keys

        var result = gradesModel.results

        for (key in keys)

            result = result.filter {
                for (value in it) {
                    if (value == selectionMap[key]!![0]) return@filter true
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
}