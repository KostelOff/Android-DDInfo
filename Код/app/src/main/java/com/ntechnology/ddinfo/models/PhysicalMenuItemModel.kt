package com.ntechnology.ddinfo.models

import android.content.res.Resources
import com.ntechnology.ddinfo.R

data class PhysicalMenuItemModel(val id: String, val title: String) {

    companion object {
        const val ID_PHYSICAL_GRADES = "grades"
        const val ID_MALE_PHYSICAL = "male"
        const val ID_FEMALE_PHYSICAL = "female"
    }
}

fun PhysicalMenuItemModel.Companion.getDefault(resources: Resources) = listOf(
    PhysicalMenuItemModel(ID_PHYSICAL_GRADES, resources.getString(R.string.physical_menu_item_grades)),
    PhysicalMenuItemModel(ID_MALE_PHYSICAL, resources.getString(R.string.physical_menu_item_male)),
    PhysicalMenuItemModel(ID_FEMALE_PHYSICAL, resources.getString(R.string.physical_menu_item_female)))