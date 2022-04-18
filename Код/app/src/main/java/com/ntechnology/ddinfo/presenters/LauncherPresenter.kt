package com.ntechnology.ddinfo.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ntechnology.ddinfo.ui.views.LauncherView
import javax.inject.Inject

@InjectViewState
class LauncherPresenter @Inject constructor() : MvpPresenter<LauncherView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showMainMenu()
    }
}