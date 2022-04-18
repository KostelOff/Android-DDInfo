package com.ntechnology.ddinfo.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ntechnology.ddinfo.models.CalculatorItemModel
import com.ntechnology.ddinfo.models.SelectionModel
import com.ntechnology.ddinfo.ui.views.SelectionView
import javax.inject.Inject

@InjectViewState
class SelectionPresenter @Inject constructor() : MvpPresenter<SelectionView>() {

    var calculatorItem: CalculatorItemModel? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setSelectionHeader(calculatorItem?.description.orEmpty())
        viewState.setSelectionItems(calculatorItem?.values.orEmpty())
    }

    fun onSelectionClick(item: SelectionModel) {
        calculatorItem?.let { calculatorItem ->
            viewState.selectItem(calculatorItem, item)
        }
    }

    fun onClearClick() {
        calculatorItem?.let { calculatorItem ->
            viewState.selectItem(calculatorItem, null)
        }
    }
}