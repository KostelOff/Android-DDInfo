package com.ntechnology.ddinfo.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ntechnology.ddinfo.models.CalculatorModel
import com.ntechnology.ddinfo.models.CalculatorStateModel
import com.squareup.moshi.Moshi

class CalculatorStorage(
    private val sharedPreferences: SharedPreferences, private val moshi: Moshi) {

    fun saveCalculatorState(calculator: CalculatorModel, calculatorState: CalculatorStateModel) {
        val json = moshi.adapter(CalculatorStateModel::class.java).toJson(calculatorState)
        sharedPreferences.edit {
            putString(calculator.id, json)
        }
    }

    fun getCalculatorState(calculator: CalculatorModel): CalculatorStateModel? {
        return try {
            val json = sharedPreferences.getString(calculator.id, "").orEmpty()
            moshi.adapter(CalculatorStateModel::class.java).fromJson(json)
        } catch (ex: Exception) {
            null
        }
    }
}