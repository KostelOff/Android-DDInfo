package com.ntechnology.ddinfo.ui.activities.gender

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.models.PhysicalMenuItemModel
import com.ntechnology.ddinfo.models.SelectedGenderGrade
import com.ntechnology.ddinfo.ui.activities.gender.selector.ExerciseAndResultSelectorActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_gender_grade.*
import javax.inject.Inject

class GenderGradeActivity : MvpAppCompatActivity(), GenderGradeView {

    companion object {
        val EX1 = 1
        val EX2 = 2
        val EX3 = 3
        val EX4 = 4
        val EX5 = 5

        val EXRES1 = 6
        val EXRES2 = 7
        val EXRES3 = 8
        val EXRES4 = 9
        val EXRES5 = 10

        val ID = "ID"

        fun start(activity: Activity, id: String) {
            val intent = Intent(activity, GenderGradeActivity::class.java)
            intent.putExtra(ID, id)
            activity.startActivity(intent)
        }
    }


    @Inject
    @InjectPresenter
    lateinit var presenter: GenderGradePresenter

    @ProvidePresenter
    fun providePresenter(): GenderGradePresenter = presenter

    override fun setTitle(name: String) {
        supportActionBar?.let { actionBar ->
            actionBar.title = name
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender_grade)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        intent.getStringExtra(ID)?.let {
            presenter.id = it

            if (PhysicalMenuItemModel.ID_FEMALE_PHYSICAL == it) {
                male_part.visibility = View.GONE
            }

        }



        exercise1.setOnClickListener { presenter.openExerciseSelection(EX1) }
        exercise2.setOnClickListener { presenter.openExerciseSelection(EX2) }
        exercise3.setOnClickListener { presenter.openExerciseSelection(EX3) }
        exercise4.setOnClickListener { presenter.openExerciseSelection(EX4) }
        exercise5.setOnClickListener { presenter.openExerciseSelection(EX5) }

        exerciseResult1.setOnClickListener { presenter.openResultSelection(EXRES1) }
        exerciseResult2.setOnClickListener { presenter.openResultSelection(EXRES2) }
        exerciseResult3.setOnClickListener { presenter.openResultSelection(EXRES3) }
        exerciseResult4.setOnClickListener { presenter.openResultSelection(EXRES4) }
        exerciseResult5.setOnClickListener { presenter.openResultSelection(EXRES5) }

        exercise1reset.setOnClickListener { presenter.onResetClick(EX1) }
        exercise2reset.setOnClickListener { presenter.onResetClick(EX2) }
        exercise3reset.setOnClickListener { presenter.onResetClick(EX3) }
        exercise4reset.setOnClickListener { presenter.onResetClick(EX4) }
        exercise5reset.setOnClickListener { presenter.onResetClick(EX5) }


    }

    override fun update(selectionMap: MutableMap<Int, SelectedGenderGrade>) {
        selectionMap[1]?.let {
            exercise1.text = it.title
            exerciseResult1.text = it.value
            exercise1reset.visibility = View.VISIBLE
        } ?: run {
            exercise1.text = ""
            exerciseResult1.text = ""
            exercise1reset.visibility = View.GONE
        }
        selectionMap[2]?.let {
            exercise2.text = it.title
            exerciseResult2.text = it.value
            exercise2reset.visibility = View.VISIBLE
        } ?: run {
            exercise2.text = ""
            exerciseResult2.text = ""
            exercise2reset.visibility = View.GONE
        }
        selectionMap[3]?.let {
            exercise3.text = it.title
            exerciseResult3.text = it.value
            exercise3reset.visibility = View.VISIBLE
        } ?: run {
            exercise3.text = ""
            exerciseResult3.text = ""
            exercise3reset.visibility = View.GONE
        }
        selectionMap[4]?.let {
            exercise4.text = it.title
            exerciseResult4.text = it.value
            exercise4reset.visibility = View.VISIBLE
        } ?: run {
            exercise4.text = ""
            exerciseResult4.text = ""
            exercise4reset.visibility = View.GONE
        }
        selectionMap[5]?.let {
            exercise5.text = it.title
            exerciseResult5.text = it.value
            exercise5reset.visibility = View.VISIBLE
        } ?: run {
            exercise5.text = ""
            exerciseResult5.text = ""
            exercise5reset.visibility = View.GONE
        }

    }

    override fun setGradesResult(genderGrade: MutableMap<Int, Int>) {
        gradeResultView.isVisible = !genderGrade.isEmpty()

        gradeResultView.gradeResultModel = genderGrade
    }

    override fun openSelector(id: Int, items: Array<String>) {
        ExerciseAndResultSelectorActivity.start(this, id, items)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(requestCode, resultCode, data)
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

}