package com.ntechnology.ddinfo.models

data class CalculatorStateModel(
    val selectionMap: Map<String, MutableList<SelectionModel>>, val inputMap: Map<String, MutableList<InputValueModel>>)