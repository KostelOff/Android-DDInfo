package com.ntechnology.ddinfo.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ntechnology.ddinfo.models.GradesSelectionItem
import com.ntechnology.ddinfo.ui.views.GradeSelectionView
import javax.inject.Inject

@InjectViewState
class GradeSelectionPresenter @Inject constructor() : MvpPresenter<GradeSelectionView>() {

    var gradesSelectionItem: GradesSelectionItem? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setSelectionHeader(gradesSelectionItem?.description.orEmpty())
        viewState.setSelectionItems(gradesSelectionItem?.values.orEmpty())
    }

    fun onSelectionClick(item: String) {
        gradesSelectionItem?.let { gradesSelectionItem ->
            viewState.selectItem(gradesSelectionItem, item)
        }
    }

    fun onClearClick() {
        gradesSelectionItem?.let { gradesSelectionItem ->
            viewState.selectItem(gradesSelectionItem, null)
        }
    }
}