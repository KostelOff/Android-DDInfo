package com.ntechnology.ddinfo.utils

import android.content.res.AssetManager
import com.ntechnology.ddinfo.extensions.safetyRead
import com.ntechnology.ddinfo.models.CalculatorModel
import com.squareup.moshi.Moshi

class CalculatorItemsProvider(
    private val assetManager: AssetManager, private val moshi: Moshi) {

    fun readCalculator(type: String): CalculatorModel? {
        return readKeyboard(
            when (type) {
                CalculatorModel.CALCULATOR_ID_DD -> CALCULATOR_JSON_DDINFO_DD
                CalculatorModel.CALCULATOR_ID_DKDSO -> CALCULATOR_JSON_DDINFO_DKDSO
                CalculatorModel.CALCULATOR_ID_DKNAEM -> CALCULATOR_JSON_DDINFO_DKNAEM
                CalculatorModel.CALCULATOR_ID_ENRISK -> CALCULATOR_JSON_DDINFO_ENRISK
                CalculatorModel.CALCULATOR_ID_EPUVOL -> CALCULATOR_JSON_DDINFO_EPUVOL
                CalculatorModel.CALCULATOR_ID_MP -> CALCULATOR_JSON_DDINFO_MP
                CalculatorModel.CALCULATOR_ID_PP -> CALCULATOR_JSON_DDINFO_PP
                CalculatorModel.CALCULATOR_ID_BENEFIT -> CALCULATOR_JSON_DDINFO_BENEFIT
                else -> null
            })
    }

    private fun readKeyboard(path: String?): CalculatorModel? {
        if (path != null) {
            return assetManager.safetyRead(path)?.let { json ->
                moshi.adapter(CalculatorModel::class.java).fromJson(json)
            }
        }
        return null
    }

    companion object {
        private const val CALCULATOR_JSON_DDINFO_DD = "ddinfo_dd.json"
        private const val CALCULATOR_JSON_DDINFO_DKDSO = "ddinfo_dkdso.json"
        private const val CALCULATOR_JSON_DDINFO_DKNAEM = "ddinfo_dknaem.json"
        private const val CALCULATOR_JSON_DDINFO_ENRISK = "ddinfo_enrisk.json"
        private const val CALCULATOR_JSON_DDINFO_EPUVOL = "ddinfo_epuvol.json"
        private const val CALCULATOR_JSON_DDINFO_MP = "ddinfo_mp.json"
        private const val CALCULATOR_JSON_DDINFO_PP = "ddinfo_pp.json"
        private const val CALCULATOR_JSON_DDINFO_BENEFIT = "ddinfo_retirement.json"
    }
}