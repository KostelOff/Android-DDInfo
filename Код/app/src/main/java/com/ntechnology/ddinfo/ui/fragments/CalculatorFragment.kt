package com.ntechnology.ddinfo.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.extensions.moveToGetValue
import com.ntechnology.ddinfo.extensions.moveToSelection
import com.ntechnology.ddinfo.models.*
import com.ntechnology.ddinfo.presenters.CalculatorPresenter
import com.ntechnology.ddinfo.ui.activities.LauncherActivity
import com.ntechnology.ddinfo.ui.adapters.CalculatorItemListAdapter
import com.ntechnology.ddinfo.ui.views.CalculatorView
import com.ntechnology.ddinfo.utils.ApplicationConstants
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_calculator.*
import javax.inject.Inject

class CalculatorFragment : MvpAppCompatFragment(), CalculatorView {
    private val adapter by lazy {
        CalculatorItemListAdapter().apply {
            clickListener = { calculatorItem, selection ->
                presenter.onCalculatorItemClick(calculatorItem, selection)
            }
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: CalculatorPresenter

    @ProvidePresenter
    fun providePresenter(): CalculatorPresenter = presenter

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { arguments ->
            presenter.id = arguments.getString(ARGUMENT_ID).orEmpty()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_calculator, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_clear -> {
                presenter.onClearClick()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_SELECTION && data != null) {
            val item = data.getParcelableExtra<CalculatorItemModel>(ApplicationConstants.INTENT_EXTRA_CALCULATOR_ITEM)
            presenter.onSelectionChoose(item, data.getParcelableExtra(ApplicationConstants.INTENT_EXTRA_SELECTION))
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_GET_VALUE && data != null) {
            val item = data.getParcelableExtra<CalculatorItemModel>(ApplicationConstants.INTENT_EXTRA_CALCULATOR_ITEM)
            presenter.onInputTypeChoose(item, data.getParcelableExtra(ApplicationConstants.INTENT_EXTRA_INPUT_VALUE))
        }
    }

    override fun setCalculator(calculator: CalculatorModel) {
        (activity as? AppCompatActivity)?.supportActionBar?.let { actionBar ->
            actionBar.title = calculator.name
        }
    }

    override fun setCalculatorItems(items: List<CalculatorItemViewModel>) {
        adapter.items = items
    }

    override fun setCalculationResult(result: CalculationResultModel?) {
        calculationResultView.isVisible = result != null
        calculationResultView.calculationResult = result
    }

    override fun showSelectionScreen(item: CalculatorItemModel, selection: SelectionModel?) {
        (activity as? LauncherActivity)?.moveToSelection(item, selection, this, REQUEST_CODE_SELECTION)
    }

    override fun showGetValueScreen(item: CalculatorItemModel, value: InputValueModel?) {
        (activity as? LauncherActivity)?.moveToGetValue(item, value, this, REQUEST_CODE_GET_VALUE)
    }

    companion object {
        private const val REQUEST_CODE_SELECTION = 10422
        private const val REQUEST_CODE_GET_VALUE = 12309

        private const val ARGUMENT_ID = "id"

        fun newInstance(id: String) = CalculatorFragment().apply {
            arguments = bundleOf(ARGUMENT_ID to id)
        }
    }
}