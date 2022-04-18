package com.ntechnology.ddinfo.models

import android.content.res.Resources
import com.ntechnology.ddinfo.R

data class MenuItemModel(
    val id: String, val title: String, val description: String) {

    companion object {
        const val ID_CALCULATOR = "calculatorDD"
        const val ID_PAYMENT_MENU = "paymentsMenu"
        const val ID_PHYSICAL = "physicalMenu"
        const val ID_BENEFIT = "benefitMenu"
    }
}

fun MenuItemModel.Companion.getMainMenuDefault(resources: Resources) = listOf(
    MenuItemModel(
        ID_CALCULATOR,
        resources.getString(R.string.main_menu_item_calculator_title),
        resources.getString(R.string.main_menu_item_calculator_description)), MenuItemModel(
        ID_PAYMENT_MENU,
        resources.getString(R.string.main_menu_item_payment_menu_title),
        resources.getString(R.string.main_menu_item_payment_menu_description)), MenuItemModel(
        ID_PHYSICAL,
        resources.getString(R.string.main_menu_item_physical_menu_title),
        resources.getString(R.string.main_menu_item_physical_menu_description)), MenuItemModel(
        ID_BENEFIT,
        resources.getString(R.string.main_menu_item_benefit_menu_title),
        resources.getString(R.string.main_menu_item_benefit_menu_description)))