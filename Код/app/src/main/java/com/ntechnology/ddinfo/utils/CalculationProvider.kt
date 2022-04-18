package com.ntechnology.ddinfo.utils

import com.ntechnology.ddinfo.models.*
import java.math.BigDecimal
import kotlin.math.roundToInt

class CalculationProvider {

    fun calculate(
        calculator: CalculatorModel,
        calculatorState: CalculatorStateModel): CalculationResultModel? {
        return when (calculator.id) {
            CalculatorModel.CALCULATOR_ID_DD -> calculateDD_BD(calculator, calculatorState)
            CalculatorModel.CALCULATOR_ID_DKDSO -> calculateDKDSO(calculator, calculatorState)
            CalculatorModel.CALCULATOR_ID_DKNAEM -> calculateDKNAEM(calculator, calculatorState)
            CalculatorModel.CALCULATOR_ID_ENRISK -> calculateENRISK(calculator, calculatorState)
            CalculatorModel.CALCULATOR_ID_EPUVOL -> calculateEPUVOL(calculator, calculatorState)
            CalculatorModel.CALCULATOR_ID_MP -> calculateMP(calculator, calculatorState)
            CalculatorModel.CALCULATOR_ID_PP -> calculatePP(calculator, calculatorState)
            CalculatorModel.CALCULATOR_ID_BENEFIT -> calculateBENEFIT(calculator, calculatorState)
            else -> null
        }
    }

    private fun calculateBENEFIT(
        cl: CalculatorModel, cst: CalculatorStateModel): CalculationResultModel? {
        if (!checkRequiredFields(cl, cst)) {
            return null
        }

        val ovz = getBigDecimal(cl, cst, "ovz")
        val ovd = getBigDecimal(cl, cst, "ovd")
        val upovd = getBigDecimal(cl, cst, "upovd", BigDecimal.ONE)

        val envl = getBigDecimal(cl, cst, "envl")
        val region = getBigDecimal(cl, cst, "region", BigDecimal.ONE)
        val serve_time = getBigDecimal(cl, cst, "serve_time")
        val retire_date = getBigDecimal(cl, cst, "retire_date")
        val veteran = getBigDecimal(cl, cst, "veteran")

        val koef = envl * region * serve_time * retire_date

        val payoff = (ovz + ovd * upovd) * koef + veteran
        return CalculationResultModel(
            payoff.setScale(2, BigDecimal.ROUND_HALF_UP).toFloat(), 0f, 0f).apply {
            payoffBD = payoff.setScale(2, BigDecimal.ROUND_HALF_UP)
        }
    }

    private fun calculateDD(
        cl: CalculatorModel, cst: CalculatorStateModel): CalculationResultModel? {
        if (!checkRequiredFields(cl, cst)) {
            return null
        }

        var isAddEnDostig = false

        cst.selectionMap.get("ovd")?.let {
            it.firstOrNull()?.let {
                when (it.title) {
                    "1 тарифный разряд", "2 тарифный разряд", "3 тарифный разряд", "4 тарифный разряд" -> {
                        isAddEnDostig = true
                    }
                    else -> {
                        isAddEnDostig = false
                    }
                }
            }
        }


        val ovz = get(cl, cst, "ovz")
        val ovd = get(cl, cst, "ovd") * get(cl, cst, "upovd", 1f)
        val envl = (ovz + ovd) * get(cl, cst, "envl")
        val enkk = ovd * get(cl, cst, "enkk")
        val enrssgt = ovd * get(cl, cst, "enrssgt")
        val enous = ovd * Math.min(1f, get(cl, cst, "enous"))
        val pnstag_spzgt = ovd * get(cl, cst, "pnstag_spzgt")
        val pnstag_shif = ovd * get(cl, cst, "pnstag_shif")
        val enur = ovd * get(cl, cst, "enur")
        val dostigs =
            ovd * Math.min(1f, (get(cl, cst, "en_dostig").minus(if (isAddEnDostig) 0.5f else 0f)))
        val risk = ovd * get(cl, cst, "en_risk")
        val region = (ovz + ovd + envl + enkk + enrssgt + enous) * get(cl, cst, "region")
        val sever = (ovz + ovd + envl + enkk + enrssgt + enous) * get(cl, cst, "sever")
        val premia = (ovz + ovd) * get(cl, cst, "premia")
        val mp = (ovz + ovd) * get(cl, cst, "mp")
        val enDostigAdd = get(cl, cst, "ovd") * if (isAddEnDostig) 0.5f else 0f
        val payoff =
            ovz + ovd + envl + enkk + enrssgt + enous + pnstag_spzgt + pnstag_shif + enur + dostigs + risk + region + sever + premia + mp + enDostigAdd

        val taxes = ((payoff - (if (mp > 0) 4000f else 0f) - get(
            cl, cst, "nalog_vishet")) * .13f).roundToInt()
        val ispol_proiz = getPercentOrValueInput(cl, cst, "ispol_proiz", payoff - taxes)
        val other_uder = getPercentOrValueInput(cl, cst, "other_uder", payoff - taxes - ispol_proiz)

        return CalculationResultModel(payoff, taxes.toFloat(), ispol_proiz + other_uder)
    }

    private fun calculateDD_BD(
        cl: CalculatorModel, cst: CalculatorStateModel): CalculationResultModel? {
        if (!checkRequiredFields(cl, cst)) {
            return null
        }

        var isAddEnDostig = false

        cst.selectionMap.get("ovd")?.let {
            it.firstOrNull()?.let {
                when (it.title) {
                    "1 тарифный разряд", "2 тарифный разряд", "3 тарифный разряд", "4 тарифный разряд" -> {
                        isAddEnDostig = true
                    }
                    else -> {
                        isAddEnDostig = false
                    }
                }
            }
        }


        val ovz = getBigDecimal(cl, cst, "ovz")
        val ovd = getBigDecimal(cl, cst, "ovd") * getBigDecimal(cl, cst, "upovd", BigDecimal.ONE)
        val envl = (ovz + ovd) * getBigDecimal(cl, cst, "envl")
        val enkk = ovd * getBigDecimal(cl, cst, "enkk")
        val enrssgt = ovd * getBigDecimal(cl, cst, "enrssgt")
        val enous = ovd * getBigDecimal(cl, cst, "enous").min(BigDecimal.ONE)
        val pnstag_spzgt = ovd * getBigDecimal(cl, cst, "pnstag_spzgt")
        val pnstag_shif = ovd * getBigDecimal(cl, cst, "pnstag_shif")
        val enur = ovd * getBigDecimal(cl, cst, "enur")
        val dostigs = ovd * (getBigDecimal(
            cl,
            cst,
            "en_dostig").minus(if (isAddEnDostig) BigDecimal(0.5) else BigDecimal.ZERO)).min(
            BigDecimal.ONE)
        val risk = ovd * getBigDecimal(cl, cst, "en_risk")
        val region = (ovz + ovd + envl + enkk + enrssgt + enous) * getBigDecimal(cl, cst, "region")
        val sever = (ovz + ovd + envl + enkk + enrssgt + enous) * getBigDecimal(cl, cst, "sever")
        val premia = (ovz + ovd) * getBigDecimal(cl, cst, "premia")
        val mp = (ovz + ovd) * getBigDecimal(cl, cst, "mp")
        val enDostigAdd =
            getBigDecimal(cl, cst, "ovd") * if (isAddEnDostig) BigDecimal(0.5) else BigDecimal.ZERO
        val payoff =
            ovz + ovd + envl + enkk + enrssgt + enous + pnstag_spzgt + pnstag_shif + enur + dostigs + risk + region + sever + premia + mp + enDostigAdd

//        val taxes = ((payoff - (if (mp > BigDecimal.ZERO) 4000f else 0f) - getBigDecimal(cl, cst, "nalog_vishet")) * .13f).roundToInt()
        val taxes =
            ((payoff - (if (mp > BigDecimal.ZERO) BigDecimal(4000) else BigDecimal.ZERO) - getBigDecimal(
                cl, cst, "nalog_vishet")) * BigDecimal(0.13)).setScale(0, BigDecimal.ROUND_HALF_UP)
        val ispol_proiz = getPercentOrValueInput(cl, cst, "ispol_proiz", payoff - taxes)
        val other_uder = getPercentOrValueInput(cl, cst, "other_uder", payoff - taxes - ispol_proiz)

        return CalculationResultModel(
            payoff.toFloat(), taxes.toFloat(), ispol_proiz.toFloat() + other_uder.toFloat()).apply {
            payoffBD = payoff
            taxesBD = taxes
            otherObligationsBD = ispol_proiz + other_uder
        }
    }

    private fun calculateDKDSO(
        cl: CalculatorModel, cst: CalculatorStateModel): CalculationResultModel? {

        if (!checkRequiredFields(cl, cst)) {
            return null
        }
        val ovz = get(cl, cst, "ovz").toDouble()
        val ovd = get(cl, cst, "ovd").toDouble()
        val days = get(cl, cst, "days").toDouble()
        val value = (ovz + ovd) / 30.0 * ((days / 3.0).toInt() * 2.0)
        val valueResult = Math.round(value * 100) / 100.0
        return CalculationResultModel(valueResult.toFloat())
    }

    private fun calculateDKNAEM(
        cl: CalculatorModel, cst: CalculatorStateModel): CalculationResultModel? {

        if (!checkRequiredFields(cl, cst)) {
            return null
        }

        val value = get(cl, cst, "famcount") * get(cl, cst, "region")
        return CalculationResultModel(value)
    }

    private fun calculateENRISK(
        cl: CalculatorModel, cst: CalculatorStateModel): CalculationResultModel? {

        if (!checkRequiredFields(cl, cst)) {
            return null
        }

        val value = get(cl, cst, "ovd") * get(cl, cst, "upovd", 1f) * get(cl, cst, "days")
        return CalculationResultModel(value)
    }

    private fun calculateEPUVOL(
        cl: CalculatorModel, cst: CalculatorStateModel): CalculationResultModel? {
        if (!checkRequiredFields(cl, cst)) {
            return null
        }

        val value = (get(cl, cst, "ovz") + get(cl, cst, "ovd")) * (get(cl, cst, "visluga") + get(
            cl, cst, "gosnagrada"))
        return CalculationResultModel(value)
    }

    private fun calculateMP(
        cl: CalculatorModel, cst: CalculatorStateModel): CalculationResultModel? {

        if (!checkRequiredFields(cl, cst)) {
            return null
        }

        val value = get(cl, cst, "ovz") + get(cl, cst, "ovd") * get(cl, cst, "upovd", 1f)
        return CalculationResultModel(value)
    }

    private fun calculatePP(
        cl: CalculatorModel, cst: CalculatorStateModel): CalculationResultModel? {

        if (!checkRequiredFields(cl, cst)) {
            return null
        }

        val value = (get(cl, cst, "ovz") + get(cl, cst, "ovd")) * (get(cl, cst, "navoen") + get(
            cl, cst, "nasemia"))
        if (cl.onlyPositive == true && value <= 0) {
            return null
        }

        return CalculationResultModel(value)
    }

    private fun checkRequiredFields(
        calculator: CalculatorModel, calculatorState: CalculatorStateModel): Boolean {
        return calculator.items.filter { item -> !item.isOptional }.all { calculatorItem ->
            when {
                calculatorItem.type == CalculatorItemModel.TYPE_INPUT -> {
                    calculatorState.inputMap[calculatorItem.id]?.isNotEmpty() ?: false
                }
                calculatorItem.type == CalculatorItemModel.TYPE_SELECTION -> {
                    calculatorState.selectionMap[calculatorItem.id]?.isNotEmpty() ?: false
                }
                else -> false
            }
        }
    }

    private fun getBigDecimal(
        calculator: CalculatorModel,
        calculatorState: CalculatorStateModel,
        id: String,
        default: BigDecimal = BigDecimal.ZERO): BigDecimal {

//        val selections = calculatorState.selectionMap[id]
//        if (selections == null || selections.isEmpty()) {
//            return default
//        } else {
//            return selections.firstOrNull()?.value?.toBigDecimal() ?: default
//        }


        val calculatorItem = calculator.items.find { it.id == id } ?: return default

        return if (calculatorItem.type == CalculatorItemModel.TYPE_SELECTION) {
            val selections = calculatorState.selectionMap[id]
            if (selections == null || selections.isEmpty()) {
                default
            } else {
                var ressult = BigDecimal.ZERO
                for (s in selections) {
                    ressult += s.value.toBigDecimal()
                }
                ressult
            }
        } else {
            val inputs = calculatorState.inputMap[id]
            if (inputs == null || inputs.isEmpty()) {
                default
            } else {
                var ressult = BigDecimal.ZERO

                for (i in inputs) {

                    if (i.type == InputValueModel.INPUT_TYPE_PERCENT) {
                        ressult += (i.value / 100f).toBigDecimal()
                    } else {
                        ressult += i.value.toBigDecimal()
                    }

                }

                ressult
            }
        }


    }

    private fun get(
        calculator: CalculatorModel,
        calculatorState: CalculatorStateModel,
        id: String,
        default: Float = 0f): Float {

        val calculatorItem = calculator.items.find { it.id == id } ?: return default

        return if (calculatorItem.type == CalculatorItemModel.TYPE_SELECTION) {
            val selections = calculatorState.selectionMap[id]
            if (selections == null || selections.isEmpty()) {
                default
            } else {
                selections.map {
                    it.value.toDouble()
                }.sumByDouble { it }.toFloat()
            }
        } else {
            val inputs = calculatorState.inputMap[id]
            if (inputs == null || inputs.isEmpty()) {
                default
            } else {
                inputs.map {
                    if (it.type == InputValueModel.INPUT_TYPE_PERCENT) {
                        (it.value / 100f).toDouble()
                    } else {
                        it.value.toDouble()
                    }
                }.sumByDouble { it }.toFloat()
            }
        }
    }

    private fun getPercentOrValueInput(
        calculator: CalculatorModel,
        calculatorState: CalculatorStateModel,
        id: String,
        value: Float,
        default: Float = 0f): Float {

        val calculatorItem = calculator.items.find { it.id == id } ?: return default

        if (calculatorItem.type == CalculatorItemModel.TYPE_INPUT && calculatorItem.inputTypes.toSet() == setOf(
                InputValueModel.INPUT_TYPE_PERCENT, InputValueModel.INPUT_TYPE_VALUE)) {
            val inputs = calculatorState.inputMap[id]
            return if (inputs == null || inputs.isEmpty()) {
                default
            } else {
                inputs.map {
                    when {
                        it.type == InputValueModel.INPUT_TYPE_VALUE -> it.value.toFloat()
                        it.type == InputValueModel.INPUT_TYPE_PERCENT -> value * it.value / 100f
                        else -> 0f
                    }
                }.map {
                    it.toDouble()
                }.sumByDouble {
                    it
                }.toFloat()
            }
        }

        return default
    }

    private fun getPercentOrValueInput(
        calculator: CalculatorModel,
        calculatorState: CalculatorStateModel,
        id: String,
        value: BigDecimal,
        default: BigDecimal = BigDecimal.ZERO): BigDecimal {

        val calculatorItem = calculator.items.find { it.id == id } ?: return default

        if (calculatorItem.type == CalculatorItemModel.TYPE_INPUT && calculatorItem.inputTypes.toSet() == setOf(
                InputValueModel.INPUT_TYPE_PERCENT, InputValueModel.INPUT_TYPE_VALUE)) {
            val inputs = calculatorState.inputMap[id]
            return if (inputs == null || inputs.isEmpty()) {
                default
            } else {

                var ressult = BigDecimal.ZERO

                for (i in inputs) {
                    ressult += when {
                        i.type == InputValueModel.INPUT_TYPE_VALUE -> i.value.toBigDecimal()
                        i.type == InputValueModel.INPUT_TYPE_PERCENT -> value * i.value.toBigDecimal() / 100f.toBigDecimal()
                        else -> BigDecimal.ZERO
                    }
                }

                ressult
            }
        }

        return default
    }
}