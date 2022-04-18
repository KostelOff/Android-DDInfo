package com.ntechnology.ddinfo.models

data class CalculatorModel(
    val id: String, val name: String, val onlyPositive: Boolean?, val items: List<CalculatorItemModel>) {

    companion object {
        const val CALCULATOR_ID_DD = "dd"
        const val CALCULATOR_ID_DKDSO = "dkdso"
        const val CALCULATOR_ID_DKNAEM = "dknaem"
        const val CALCULATOR_ID_ENRISK = "enrisk"
        const val CALCULATOR_ID_EPUVOL = "epuvol"
        const val CALCULATOR_ID_MP = "mp"
        const val CALCULATOR_ID_PP = "pp"
        const val CALCULATOR_ID_BENEFIT = "retirement"
    }
}