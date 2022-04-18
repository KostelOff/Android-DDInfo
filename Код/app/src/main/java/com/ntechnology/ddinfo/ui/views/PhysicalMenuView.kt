package com.ntechnology.ddinfo.ui.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.ntechnology.ddinfo.models.PhysicalMenuItemModel

interface PhysicalMenuView : MvpView {
    fun setMenuItems(items: List<PhysicalMenuItemModel>)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun moveToGrades(physicalMenuItem: PhysicalMenuItemModel)
}