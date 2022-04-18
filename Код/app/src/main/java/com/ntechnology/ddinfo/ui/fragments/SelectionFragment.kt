package com.ntechnology.ddinfo.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import androidx.core.os.bundleOf
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.extensions.moveToPrevious
import com.ntechnology.ddinfo.models.CalculatorItemModel
import com.ntechnology.ddinfo.models.SelectionModel
import com.ntechnology.ddinfo.presenters.SelectionPresenter
import com.ntechnology.ddinfo.ui.activities.LauncherActivity
import com.ntechnology.ddinfo.ui.adapters.SelectionListAdapter
import com.ntechnology.ddinfo.ui.decorators.DividerDecorator
import com.ntechnology.ddinfo.ui.views.SelectionView
import com.ntechnology.ddinfo.utils.ApplicationConstants
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_payments.*
import javax.inject.Inject

class SelectionFragment : MvpAppCompatFragment(), SelectionView {
    private val adapter by lazy {
        SelectionListAdapter(getCalculatorItem()?.id ?: "").apply {
            clickListener = { selection ->
                presenter.onSelectionClick(selection)
            }
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: SelectionPresenter

    @ProvidePresenter
    fun providePresenter(): SelectionPresenter = presenter

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCalculatorItem()?.let { item ->
            presenter.calculatorItem = item
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_selection, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.let { actionBar ->
            actionBar.title = getCalculatorItem()?.name.orEmpty()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerDecorator(context))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        if (getSelectionItem() != null) {
            inflater?.inflate(R.menu.menu_selection, menu)
        }
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

    override fun setSelectionHeader(title: String) {
        adapter.headerTitle = title
    }

    override fun setSelectionItems(items: List<SelectionModel>) {
        adapter.items = items
    }

    override fun selectItem(calculatorItem: CalculatorItemModel, selection: SelectionModel?) {
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, Intent().apply {
            putExtra(ApplicationConstants.INTENT_EXTRA_CALCULATOR_ITEM, calculatorItem)
            putExtra(ApplicationConstants.INTENT_EXTRA_SELECTION, selection)
        })
        (activity as? LauncherActivity)?.moveToPrevious()
    }

    private fun getCalculatorItem(): CalculatorItemModel? {
        return arguments?.getParcelable(ARGUMENT_CALCULATOR_ITEM)
    }

    private fun getSelectionItem(): SelectionModel? {
        return arguments?.getParcelable(ARGUMENT_SELECTION)
    }

    companion object {
        private const val ARGUMENT_CALCULATOR_ITEM = "calculatorItem"
        private const val ARGUMENT_SELECTION = "selection"
        fun newInstance(item: CalculatorItemModel, selection: SelectionModel?) = SelectionFragment().apply {
            arguments = bundleOf(
                ARGUMENT_CALCULATOR_ITEM to item, ARGUMENT_SELECTION to selection)
        }
    }
}