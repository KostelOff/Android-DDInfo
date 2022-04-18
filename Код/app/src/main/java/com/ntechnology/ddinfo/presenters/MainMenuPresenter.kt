package com.ntechnology.ddinfo.presenters

import android.content.res.Resources
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ntechnology.ddinfo.models.MenuItemModel
import com.ntechnology.ddinfo.models.getMainMenuDefault
import com.ntechnology.ddinfo.ui.views.MainMenuView
import javax.inject.Inject

@InjectViewState
class MainMenuPresenter @Inject constructor(private val resources: Resources) : MvpPresenter<MainMenuView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setMenuItems(MenuItemModel.getMainMenuDefault(resources))
    }

    fun onMenuClick(item: MenuItemModel) {
        when (item.id) {
            MenuItemModel.ID_CALCULATOR -> {
                viewState.showCalculator()
            }
            MenuItemModel.ID_PAYMENT_MENU -> {
                viewState.showPaymentMenu()
            }
            MenuItemModel.ID_PHYSICAL -> {
                viewState.showPhysicalMenu()
            }
            MenuItemModel.ID_BENEFIT -> {
                viewState.showBenefitCalculator()
            }
        }
    }

}