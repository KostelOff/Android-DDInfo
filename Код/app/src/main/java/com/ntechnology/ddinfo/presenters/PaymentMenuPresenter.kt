package com.ntechnology.ddinfo.presenters

import android.content.res.Resources
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ntechnology.ddinfo.models.PaymentMenuItemModel
import com.ntechnology.ddinfo.models.getDefault
import com.ntechnology.ddinfo.ui.views.PaymentMenuView
import javax.inject.Inject

@InjectViewState
class PaymentMenuPresenter @Inject constructor(private val resources: Resources) : MvpPresenter<PaymentMenuView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setMenuItems(PaymentMenuItemModel.getDefault(resources))
    }

    fun onMenuClick(item: PaymentMenuItemModel) {
        viewState.showCalculator(item)
    }
}