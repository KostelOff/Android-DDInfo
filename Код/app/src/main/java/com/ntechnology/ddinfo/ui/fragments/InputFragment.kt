package com.ntechnology.ddinfo.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.extensions.moveToPrevious
import com.ntechnology.ddinfo.models.CalculatorItemModel
import com.ntechnology.ddinfo.models.InputValueModel
import com.ntechnology.ddinfo.presenters.InputPresenter
import com.ntechnology.ddinfo.ui.activities.LauncherActivity
import com.ntechnology.ddinfo.ui.views.InputView
import com.ntechnology.ddinfo.ui.widgets.InputValueTypeSelectorView
import com.ntechnology.ddinfo.utils.ApplicationConstants
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_input.*
import javax.inject.Inject

class InputFragment : MvpAppCompatFragment(), InputView {

    @Inject
    @InjectPresenter
    lateinit var presenter: InputPresenter

    @ProvidePresenter
    fun providePresenter(): InputPresenter = presenter

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCalculatorItem()?.let { item ->
            presenter.calculatorItem = item
        }
        getInputValue()?.let { item ->
            presenter.inputValue = item
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        editTextView.requestFocus()
        (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(
            editTextView, InputMethodManager.SHOW_IMPLICIT)

        editTextView.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                saveInputValue()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    override fun onStop() {
        view?.let { view ->
            (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
                view.windowToken, 0)
        }
        super.onStop()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.let { actionBar ->
            actionBar.title = getCalculatorItem()?.name.orEmpty()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_input, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_save -> {
                saveInputValue()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun setCalculatorItem(item: CalculatorItemModel) {
        editTextView.hint = item.name

        if (isApplicableForItem(item)) {
            selectorView.isVisible = true
        }

        if (item.floatAllows == true) {
            editTextView.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
    }

    override fun setInputValue(item: InputValueModel) {
        editTextView.setText(item.getTruncatedString())
        editTextView.setSelection(editTextView.text.length)

        if (item.type == InputValueModel.INPUT_TYPE_PERCENT) {
            selectorView.setPercentType()
        } else if (item.type == InputValueModel.INPUT_TYPE_VALUE) {
            selectorView.setCurrencyType()
        }
    }

    override fun showDefaultError() {
        context?.toast(resources.getString(R.string.input_default_error), Toast.LENGTH_SHORT)
    }

    override fun showEmptyStringError() {
        context?.toast(resources.getString(R.string.input_empty_string_error), Toast.LENGTH_SHORT)
    }

    override fun showMaxValueError(maxValue: Int) {
        context?.toast(resources.getString(R.string.input_max_value_error).format(maxValue), Toast.LENGTH_SHORT)
    }

    override fun showMaxPercentError(maxPercent: Int) {
        context?.toast(resources.getString(R.string.input_max_percent_error).format(maxPercent), Toast.LENGTH_SHORT)
    }

    override fun showNotValueError() {
        context?.toast(resources.getString(R.string.input_not_value_error), Toast.LENGTH_SHORT)
    }

    override fun showEmptyInputTypeError() {
        context?.toast(resources.getString(R.string.input_empty_input_type_error), Toast.LENGTH_SHORT)
    }

    override fun showAmbiguousInputTypeError() {
        context?.toast(resources.getString(R.string.input_ambiguous_input_type_error), Toast.LENGTH_SHORT)
    }

    override fun selectInputValue(value: InputValueModel?, calculatorItem: CalculatorItemModel) {
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, Intent().apply {
            putExtra(ApplicationConstants.INTENT_EXTRA_CALCULATOR_ITEM, calculatorItem)
            putExtra(ApplicationConstants.INTENT_EXTRA_INPUT_VALUE, value)
        })
        (activity as? LauncherActivity)?.moveToPrevious()
    }

    private fun getCalculatorItem(): CalculatorItemModel? {
        return arguments?.getParcelable(ARGUMENT_CALCULATOR_ITEM)
    }

    private fun getInputValue(): InputValueModel? {
        return arguments?.getParcelable(ARGUMENT_INPUT_VALUE)
    }

    private fun getCalculatorItemType(): String? {
        return when (selectorView.getInputValueType()) {
            InputValueTypeSelectorView.INPUT_TYPE_PERCENT -> InputValueModel.INPUT_TYPE_PERCENT
            InputValueTypeSelectorView.INPUT_TYPE_CURRENCY -> InputValueModel.INPUT_TYPE_VALUE
            else -> null
        }
    }

    private fun isApplicableForItem(item: CalculatorItemModel): Boolean {
        return item.inputTypes.toSet() == setOf(InputValueModel.INPUT_TYPE_PERCENT, InputValueModel.INPUT_TYPE_VALUE)
    }

    private fun saveInputValue() {
        presenter.onSaveClick(
            editTextView.text.toString(), if (selectorView.isVisible) getCalculatorItemType() else null)
    }

    companion object {
        private const val ARGUMENT_CALCULATOR_ITEM = "calculatorItem"
        private const val ARGUMENT_INPUT_VALUE = "inputValue"

        fun newInstance(item: CalculatorItemModel, value: InputValueModel?) = InputFragment().apply {
            arguments = bundleOf(ARGUMENT_CALCULATOR_ITEM to item, ARGUMENT_INPUT_VALUE to value)
        }
    }
}