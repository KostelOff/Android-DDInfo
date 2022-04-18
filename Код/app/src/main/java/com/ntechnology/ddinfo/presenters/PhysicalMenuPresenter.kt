package com.ntechnology.ddinfo.presenters

import android.content.res.Resources
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ntechnology.ddinfo.models.PhysicalMenuItemModel
import com.ntechnology.ddinfo.models.getDefault
import com.ntechnology.ddinfo.ui.views.PhysicalMenuView
import javax.inject.Inject

@InjectViewState
class PhysicalMenuPresenter @Inject constructor(private val resources: Resources) : MvpPresenter<PhysicalMenuView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setMenuItems(PhysicalMenuItemModel.getDefault(resources))
    }

    fun onMenuClick(item: PhysicalMenuItemModel) {
        viewState.moveToGrades(item)
    }
}