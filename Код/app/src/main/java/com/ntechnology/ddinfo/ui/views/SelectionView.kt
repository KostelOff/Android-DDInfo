package com.ntechnology.ddinfo.ui.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.ntechnology.ddinfo.models.CalculatorItemModel
import com.ntechnology.ddinfo.models.SelectionModel

interface SelectionView : MvpView {
    fun setSelectionHeader(title: String)
    fun setSelectionItems(items: List<SelectionModel>)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun selectItem(calculatorItem: CalculatorItemModel, selection: SelectionModel?)
}