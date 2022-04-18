package com.ntechnology.ddinfo.ui.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.models.GradeResultModel
import com.ntechnology.ddinfo.models.GradesModel
import com.ntechnology.ddinfo.models.GradesSelectionItem
import com.ntechnology.ddinfo.models.GradesSelectionViewItem
import com.ntechnology.ddinfo.presenters.GradesPresenter
import com.ntechnology.ddinfo.ui.adapters.GradeItemsListAdapter
import com.ntechnology.ddinfo.ui.views.GradesView
import com.ntechnology.ddinfo.utils.ApplicationConstants
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.fragment_grade.*
import javax.inject.Inject

class GradesFragment : MvpAppCompatActivity(), GradesView {
    private val adapter by lazy {
        GradeItemsListAdapter().apply {
            clickListener = { gradeItem, selection ->
                presenter.onGradeItemClick(gradeItem, selection)
            }
        }
    }
    @Inject
    @InjectPresenter
    lateinit var presenter: GradesPresenter

    @ProvidePresenter
    fun providePresenter(): GradesPresenter = presenter


    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_grade)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        intent.getStringExtra(ARGUMENT_ID)?.let {
            presenter.id = it

        }


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_calculator, menu)
        return true
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_SELECTION && data != null) {
            val item = data.getParcelableExtra<GradesSelectionItem>(ApplicationConstants.INTENT_EXTRA_CALCULATOR_ITEM)
            presenter.onSelectionChoose(item, data.getStringExtra(ApplicationConstants.INTENT_EXTRA_SELECTION))
        }
    }

    override fun setGrades(grades: GradesModel) {
        supportActionBar?.let { actionBar ->
            actionBar.title = grades.name
        }
    }

    override fun setGradesItems(gradesItemGrades: List<GradesSelectionViewItem>) {
        adapter.items = gradesItemGrades
    }

    override fun setGradesResult(result: ArrayList<GradeResultModel>?) {
        gradeResultView.isVisible = result != null
        gradeResultView.gradeResultModel = result
    }

    override fun showError() {

        gradeResultView.isVisible = true
        gradeResultView.showError()
    }

    override fun showSelectionScreen(item: GradesSelectionItem, selection: String?) {

        GradeSelectionFragment.startForResult(this, item, selection, REQUEST_CODE_SELECTION)

    }


    companion object {
        private const val REQUEST_CODE_SELECTION = 10422

        private const val ARGUMENT_ID = "id"

        fun start(activity: MvpAppCompatActivity, id: String) {
            val intent = Intent(activity, GradesFragment::class.java)
            intent.putExtra(ARGUMENT_ID, id)
            activity.startActivity(intent)
        }
    }
}