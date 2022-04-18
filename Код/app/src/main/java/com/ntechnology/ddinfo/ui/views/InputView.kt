package com.ntechnology.ddinfo.ui.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.ntechnology.ddinfo.models.CalculatorItemModel
import com.ntechnology.ddinfo.models.InputValueModel

interface InputView : MvpView {
    fun setCalculatorItem(item: CalculatorItemModel)
    fun setInputValue(item: InputValueModel)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showDefaultError()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showEmptyStringError()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMaxValueError(maxValue: Int)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMaxPercentError(maxPercent: Int)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showNotValueError()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showAmbiguousInputTypeError()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showEmptyInputTypeError()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun selectInputValue(value: InputValueModel?, calculatorItem: CalculatorItemModel)
}