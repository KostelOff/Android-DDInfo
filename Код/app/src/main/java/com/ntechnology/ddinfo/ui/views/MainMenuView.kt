package com.ntechnology.ddinfo.ui.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.ntechnology.ddinfo.models.MenuItemModel

interface MainMenuView : MvpView {
    fun setMenuItems(items: List<MenuItemModel>)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showCalculator()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showBenefitCalculator()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showPaymentMenu()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showPhysicalMenu()
}