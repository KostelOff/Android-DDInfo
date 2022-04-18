package com.ntechnology.ddinfo.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ntechnology.ddinfo.models.*
import com.ntechnology.ddinfo.ui.views.CalculatorView
import com.ntechnology.ddinfo.utils.CalculationProvider
import com.ntechnology.ddinfo.utils.CalculatorItemsProvider
import com.ntechnology.ddinfo.utils.CalculatorStorage
import javax.inject.Inject

@InjectViewState
class CalculatorPresenter @Inject constructor(
    private val calculationProvider: CalculationProvider,
    private val calculatorStorage: CalculatorStorage,
    private val calculatorItemsProvider: CalculatorItemsProvider) : MvpPresenter<CalculatorView>() {

    private val calculator by lazy {
        calculatorItemsProvider.readCalculator(id)
    }
    private var selectionMap = hashMapOf<String, MutableList<SelectionModel>>()
    private var inputMap = hashMapOf<String, MutableList<InputValueModel>>()

    private var lastClickedPosition = -1

    var id = ""

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        calculator?.let { calculator ->
            calculatorStorage.getCalculatorState(calculator)?.let { state ->
                selectionMap = HashMap(state.selectionMap)
                inputMap = HashMap(state.inputMap)
            }

            viewState.setCalculator(calculator)
            setCalculationViews()
        }
    }

    override fun detachView(view: CalculatorView?) {
        calculator?.let {
            calculatorStorage.saveCalculatorState(it, CalculatorStateModel(selectionMap, inputMap))
        }
        super.detachView(view)
    }

    fun onCalculatorItemClick(item: CalculatorItemModel, selection: SelectionModel?) {
        lastClickedPosition = getSelectionList(item).indexOf(selection)
        if (item.type == CalculatorItemModel.TYPE_SELECTION) {
            viewState.showSelectionScreen(item, selection)
        } else if (item.type == CalculatorItemModel.TYPE_INPUT) {
            viewState.showGetValueScreen(
                item, if (lastClickedPosition >= 0) {
                    getInputValueList(item)[lastClickedPosition]
                } else {
                    null
                })
        }
    }

    fun onClearClick() {
        selectionMap.clear()
        inputMap.clear()
        setCalculationViews()
    }

    fun onSelectionChoose(item: CalculatorItemModel, selection: SelectionModel?) {
        val previousSelection = getLastClickedSelection(item)

        if (previousSelection == null && selection == null) {
            return
        }
        if (previousSelection == null && selection != null) {
            getSelectionList(item).add(selection)
        }
        if (previousSelection != null && selection == null) {
            getSelectionList(item).remove(previousSelection)
        }
        if (previousSelection != null && selection != null && lastClickedPosition >= 0) {
            getSelectionList(item)[lastClickedPosition] = selection
        }

        setCalculationViews()
    }

    fun onInputTypeChoose(item: CalculatorItemModel, value: InputValueModel?) {
        val previousInputValue = getLastClickedInputValue(item)

        if (previousInputValue == null && value == null) {
            return
        }
        if (previousInputValue == null && value != null) {
            getInputValueList(item).add(value)
        }
        if (previousInputValue != null && value == null) {
            getInputValueList(item).remove(previousInputValue)
        }
        if (previousInputValue != null && value != null && lastClickedPosition >= 0) {
            getInputValueList(item)[lastClickedPosition] = value
        }

        onSelectionChoose(item,
            value?.let { SelectionModel(value.getTitle(), value.value.toFloat()) })
    }

    private fun getSelectionList(item: CalculatorItemModel): MutableList<SelectionModel> {
        val key = item.id
        if (!selectionMap.containsKey(key)) {
            selectionMap[key] = mutableListOf()
        }
        return selectionMap[key] ?: mutableListOf()
    }

    private fun getInputValueList(item: CalculatorItemModel): MutableList<InputValueModel> {
        val key = item.id
        if (!inputMap.containsKey(key)) {
            inputMap[key] = mutableListOf()
        }
        return inputMap[key] ?: mutableListOf()
    }

    private fun getLastClickedSelection(item: CalculatorItemModel): SelectionModel? {
        var selection: SelectionModel? = null
        if (lastClickedPosition >= 0) {
            selection = getSelectionList(item)[lastClickedPosition]
        }
        return selection
    }

    private fun getLastClickedInputValue(item: CalculatorItemModel): InputValueModel? {
        var selection: InputValueModel? = null
        if (lastClickedPosition >= 0) {
            selection = getInputValueList(item)[lastClickedPosition]
        }
        return selection
    }

    private fun setCalculationViews() {

        selectionMap.get("ovd")?.let {
            it.firstOrNull()?.let {
                when (it.title) {
                    "1 тарифный разряд", "2 тарифный разряд", "3 тарифный разряд", "4 тарифный разряд" -> {
                        addEnDostig()
                    }
                    else -> {
                        removeEnDostig()
                    }
                }
            }
        } ?: run {
            removeEnDostig()
        }

        viewState.setCalculatorItems(getCalculatorViewItems())
        calculator?.let {
            viewState.setCalculationResult(
                calculationProvider.calculate(it, CalculatorStateModel(selectionMap, inputMap)))
        }
    }

    private fun removeEnDostig() {
        selectionMap.get("en_dostig")?.let {
            for (sel in it) {
                if (sel.title == enDostigAdditional.title) {
                    it.remove(sel)
                    return
                }
            }
        }
    }

    val enDostigAdditional = SelectionModel("ЕН за достижения с 1 по 4 т.р. - 50%", 0.5f)

    private fun addEnDostig() {
        selectionMap.get("en_dostig")?.let {
            for (sel in it) {
                if (sel.title == enDostigAdditional.title) return
            }
            it.add(0, enDostigAdditional)
        } ?: run {
            selectionMap.put("en_dostig", mutableListOf(enDostigAdditional))
        }
    }

    private fun getCalculatorViewItems(): List<CalculatorItemViewModel> {
        return calculator?.items?.map { CalculatorItemViewModel(it, selectionMap[it.id].orEmpty()) }
            .orEmpty()
    }
}