package com.ntechnology.ddinfo.ui.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface LauncherView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMainMenu()
}