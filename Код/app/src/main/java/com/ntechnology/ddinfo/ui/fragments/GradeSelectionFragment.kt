package com.ntechnology.ddinfo.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import androidx.core.os.bundleOf
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.models.GradesSelectionItem
import com.ntechnology.ddinfo.presenters.GradeSelectionPresenter
import com.ntechnology.ddinfo.ui.adapters.GradeSelectionListAdapter
import com.ntechnology.ddinfo.ui.decorators.DividerDecorator
import com.ntechnology.ddinfo.ui.views.GradeSelectionView
import com.ntechnology.ddinfo.utils.ApplicationConstants
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.fragment_payments.*
import javax.inject.Inject

class GradeSelectionFragment : MvpAppCompatActivity(), GradeSelectionView {
    private val adapter by lazy {
        GradeSelectionListAdapter(getGradeItem()?.title ?: "").apply {
            clickListener = { selection ->
                presenter.onSelectionClick(selection)
            }
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: GradeSelectionPresenter

    @ProvidePresenter
    fun providePresenter(): GradeSelectionPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_selection)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportActionBar?.let { actionBar ->
            actionBar.title = getGradeItem()?.title.orEmpty()
        }

        getGradeItem()?.let { item ->
            presenter.gradesSelectionItem = item
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerDecorator(this))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        if (getSelectionItem() != null) {
            inflater.inflate(R.menu.menu_selection, menu)
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_clear -> {
                presenter.onClearClick()
                true
            }
            android.R.id.home -> {
                onBackPressed()
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

    override fun setSelectionItems(items: List<String>) {
        adapter.items = items
    }

    override fun selectItem(gradesSelectionItem: GradesSelectionItem, selection: String?) {

        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(ApplicationConstants.INTENT_EXTRA_CALCULATOR_ITEM, gradesSelectionItem)
            putExtra(ApplicationConstants.INTENT_EXTRA_SELECTION, selection)
        })

        finish()
    }

    private fun getGradeItem(): GradesSelectionItem? {
        return intent.extras?.getParcelable(ARGUMENT_CALCULATOR_ITEM)
    }

    private fun getSelectionItem(): String? {
        return intent.extras?.getString(ARGUMENT_SELECTION)
    }

    companion object {
        private const val ARGUMENT_CALCULATOR_ITEM = "calculatorItem"
        private const val ARGUMENT_SELECTION = "selection"
//        fun newInstance(item: GradesSelectionItem, selection: String?) = GenderGradeSelectionFragment().apply {
//            arguments = bundleOf(
//                ARGUMENT_CALCULATOR_ITEM to item, ARGUMENT_SELECTION to selection)
//        }

        fun startForResult(
            activity: MvpAppCompatActivity, item: GradesSelectionItem, selection: String?, requestCode: Int) {

            val intent = Intent(activity, GradeSelectionFragment::class.java)
//            intent.putExtra(ARGUMENT_CALCULATOR_ITEM, item)
//            intent.putExtra(ARGUMENT_SELECTION, selection)

            val arguments = bundleOf(
                ARGUMENT_CALCULATOR_ITEM to item, ARGUMENT_SELECTION to selection)

            intent.putExtras(arguments)

            activity.startActivityForResult(intent, requestCode)

        }
    }
}