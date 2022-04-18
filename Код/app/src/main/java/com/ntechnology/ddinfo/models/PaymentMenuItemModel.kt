package com.ntechnology.ddinfo.models

import android.content.res.Resources
import com.ntechnology.ddinfo.R

data class PaymentMenuItemModel(val id: String, val title: String, val calculatorType: String) {

    companion object {
        const val ID_ALLOWANCE = "calculatorDD"
        const val ID_MATERIAL_AID = "payments"
        const val ID_ESN = "esn"
        const val ID_DK_DSO = "dkDso"
        const val ID_QUIT = "quit"
        const val ID_HIRED = "hired"
    }
}

fun PaymentMenuItemModel.Companion.getDefault(resources: Resources) = listOf(
    PaymentMenuItemModel(
        ID_ALLOWANCE,
        resources.getString(R.string.payment_menu_item_pp),
        CalculatorModel.CALCULATOR_ID_PP),
    PaymentMenuItemModel(
        ID_MATERIAL_AID,
        resources.getString(R.string.payment_menu_item_mp),
        CalculatorModel.CALCULATOR_ID_MP),
    PaymentMenuItemModel(
        ID_ESN,
        resources.getString(R.string.payment_menu_item_risk),
        CalculatorModel.CALCULATOR_ID_ENRISK),
    PaymentMenuItemModel(
        ID_DK_DSO,
        resources.getString(R.string.payment_menu_item_dkdso),
        CalculatorModel.CALCULATOR_ID_DKDSO),
    PaymentMenuItemModel(
        ID_QUIT,
        resources.getString(R.string.payment_menu_item_epuvol),
        CalculatorModel.CALCULATOR_ID_EPUVOL),
    PaymentMenuItemModel(
        ID_HIRED,
        resources.getString(R.string.payment_menu_item_dknaem),
        CalculatorModel.CALCULATOR_ID_DKNAEM))