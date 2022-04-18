package com.ntechnology.ddinfo.ui.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.ntechnology.ddinfo.models.*

interface CalculatorView : MvpView {
    fun setCalculator(calculator: CalculatorModel)
    fun setCalculatorItems(items: List<CalculatorItemViewModel>)
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setCalculationResult(result: CalculationResultModel?)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSelectionScreen(item: CalculatorItemModel, selection: SelectionModel?)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showGetValueScreen(item: CalculatorItemModel, value: InputValueModel?)
}