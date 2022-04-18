package com.ntechnology.ddinfo.ui.views

import com.arellomobile.mvp.MvpView
import com.ntechnology.ddinfo.models.*

interface GradesView : MvpView {
    fun setGrades(grades: GradesModel)
    abstract fun setGradesItems(gradesItemGrades: List<GradesSelectionViewItem>)
    fun showSelectionScreen(item: GradesSelectionItem, selection: String?)
    fun setGradesResult(result: ArrayList<GradeResultModel>?)
    fun showError()
}