package com.ntechnology.ddinfo.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ntechnology.ddinfo.models.CalculatorItemModel
import com.ntechnology.ddinfo.models.InputValueModel
import com.ntechnology.ddinfo.ui.views.InputView
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class InputPresenter @Inject constructor() : MvpPresenter<InputView>() {
    var calculatorItem: CalculatorItemModel? = null
    var inputValue: InputValueModel? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        calculatorItem?.let {
            viewState.setCalculatorItem(it)
        }
        inputValue?.let {
            viewState.setInputValue(it)
        }
    }

    fun onSaveClick(value: String, type: String?) {
        calculatorItem?.let { item ->
            when {
                value.isBlank() -> {
                    viewState.selectInputValue(null, item)
                }
                item.inputTypes.size > 1 && type == null -> {
                    viewState.showAmbiguousInputTypeError()
                }
                else -> {
                    try {
                        val amount = value.toFloat()
                        val inputType = type ?: item.inputTypes.firstOrNull()
                        if (inputType == null) {
                            viewState.showEmptyInputTypeError()
                        } else if (inputType == InputValueModel.INPUT_TYPE_PERCENT && amount > 100) {
                            viewState.showMaxPercentError(100)
                        } else if (item.maxInputValue != null && item.maxInputValue > 0 && item.maxInputValue < amount) {
                            viewState.showMaxValueError(item.maxInputValue)
                        } else {
                            viewState.selectInputValue(InputValueModel(amount, inputType), item)
                        }
                    } catch (ex: NumberFormatException) {
                        Timber.e(ex)
                        viewState.showNotValueError()
                    } catch (ex: Exception) {
                        Timber.e(ex)
                        viewState.showDefaultError()
                    }
                }
            }
        }
    }
}