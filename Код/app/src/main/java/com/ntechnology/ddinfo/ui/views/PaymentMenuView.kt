package com.ntechnology.ddinfo.ui.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.ntechnology.ddinfo.models.PaymentMenuItemModel

interface PaymentMenuView : MvpView {
    fun setMenuItems(items: List<PaymentMenuItemModel>)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showCalculator(paymentMenuItem: PaymentMenuItemModel)
}