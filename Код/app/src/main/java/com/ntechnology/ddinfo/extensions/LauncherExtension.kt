package com.ntechnology.ddinfo.extensions

import android.support.v4.app.Fragment
import com.ntechnology.ddinfo.models.CalculatorItemModel
import com.ntechnology.ddinfo.models.InputValueModel
import com.ntechnology.ddinfo.models.SelectionModel
import com.ntechnology.ddinfo.ui.activities.LauncherActivity
import com.ntechnology.ddinfo.ui.activities.gender.GenderGradeActivity
import com.ntechnology.ddinfo.ui.fragments.*

fun LauncherActivity.moveToPrevious() {
    onBackPressed()
}

fun LauncherActivity.moveToMainMenu() {
    replaceFragment(MainMenuFragment.newInstance(), false)
}

fun LauncherActivity.moveToPaymentMenu() {
    replaceFragment(PaymentMenuFragment.newInstance())
}

fun LauncherActivity.moveToPhysicalMenu() {
    replaceFragment(PhysicalMenuFragment.newInstance())
}

fun LauncherActivity.moveToCalculator(id: String) {
    replaceFragment(CalculatorFragment.newInstance(id))
}

fun LauncherActivity.moveToGrades(id: String) {
    GradesFragment.start(this, id)
}

fun LauncherActivity.moveToGenderGrades(id: String) {
    GenderGradeActivity.start(this, id)
}

fun LauncherActivity.moveToSelection(
    item: CalculatorItemModel, selection: SelectionModel?, target: Fragment, requestCode: Int) {

    replaceFragment(SelectionFragment.newInstance(item, selection).apply {
        setTargetFragment(target, requestCode)
    })
}

fun LauncherActivity.moveToGetValue(
    item: CalculatorItemModel, value: InputValueModel?, target: Fragment, requestCode: Int) {

    replaceFragment(InputFragment.newInstance(item, value).apply {
        setTargetFragment(target, requestCode)
    })
}