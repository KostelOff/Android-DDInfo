package com.ntechnology.ddinfo.ui.activities.gender

import com.arellomobile.mvp.MvpView
import com.ntechnology.ddinfo.models.SelectedGenderGrade

interface GenderGradeView : MvpView {
    fun openSelector(id: Int, items: Array<String>)
    fun update(selectionMap: MutableMap<Int, SelectedGenderGrade>)
    fun setTitle(name: String)
    fun setGradesResult(genderGrade: MutableMap<Int, Int>)
}