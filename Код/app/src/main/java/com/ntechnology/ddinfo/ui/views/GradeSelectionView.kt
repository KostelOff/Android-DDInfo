package com.ntechnology.ddinfo.ui.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.ntechnology.ddinfo.models.CalculatorItemModel
import com.ntechnology.ddinfo.models.GradesSelectionItem
import com.ntechnology.ddinfo.models.SelectionModel

interface GradeSelectionView : MvpView {
    fun setSelectionHeader(title: String)
    fun setSelectionItems(items: List<String>)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun selectItem(gradesSelectionItem: GradesSelectionItem, selection: String?)
}