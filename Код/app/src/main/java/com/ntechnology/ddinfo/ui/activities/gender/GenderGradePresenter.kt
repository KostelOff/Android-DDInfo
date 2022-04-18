package com.ntechnology.ddinfo.ui.activities.gender

import android.app.Activity
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ntechnology.ddinfo.models.SelectedGenderGrade
import com.ntechnology.ddinfo.ui.activities.gender.selector.ExerciseAndResultSelectorActivity
import com.ntechnology.ddinfo.utils.GenderGradeItemsProvider
import com.ntechnology.ddinfo.utils.GradeStorage
import com.ntechnology.ddinfo.utils.GradesProvider
import javax.inject.Inject

@InjectViewState
class GenderGradePresenter @Inject constructor(
    private val gradesProvider: GradesProvider,
    private val gradeStorage: GradeStorage,
    private val gradeItemsProvider: GenderGradeItemsProvider) : MvpPresenter<GenderGradeView>() {

    private var selectionMap = mutableMapOf<Int, SelectedGenderGrade>()


    private val grades by lazy {
        gradeItemsProvider.readGrade(id)
    }

    var id = ""

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        grades?.let { grades ->
            gradeStorage.getGenderGradeState(grades)?.let { state ->
                selectionMap = state
            }

            viewState.setTitle(grades.name)
            updateViews()
        }
    }


    override fun detachView(view: GenderGradeView?) {
        grades?.let {
            gradeStorage.saveGradeState(it, selectionMap)
        }
        super.detachView(view)
    }


    fun openExerciseSelection(id: Int) {

        grades?.let {
            val items = Array(it.items.size) { i -> it.items[i].title }
            viewState.openSelector(id, items)
        }


    }

    fun openResultSelection(id: Int) {
        grades?.let {

            val title = selectionMap[id - 5]?.title ?: ""

            if (title.isEmpty()) return

            for (item in it.items) {
                if (item.title.equals(title)) {

                    val items = Array(item.value.size) { i -> item.value[i][1] }
                    viewState.openSelector(id, items)
                }
            }


        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val selected = data!!.getStringExtra(ExerciseAndResultSelectorActivity.SELECTED)
            when (requestCode) {
                GenderGradeActivity.EX1, GenderGradeActivity.EX2, GenderGradeActivity.EX3, GenderGradeActivity.EX4, GenderGradeActivity.EX5 -> {
                    selectionMap[requestCode] = SelectedGenderGrade(selected)
                }
                GenderGradeActivity.EXRES1, GenderGradeActivity.EXRES2, GenderGradeActivity.EXRES3, GenderGradeActivity.EXRES4, GenderGradeActivity.EXRES5 -> {

                    selectionMap[requestCode - 5]?.let {
                        it.value = selected
                    }

                }

            }

            updateViews()
        }
    }

    private fun updateViews() {
        viewState.update(selectionMap)

        grades?.let { viewState.setGradesResult(gradesProvider.getGenderGrade(it, selectionMap)) }
    }

    fun onClearClick() {
        selectionMap.clear()
        updateViews()
    }

    fun onResetClick(exercise: Int) {
        selectionMap.remove(exercise)
        updateViews()
    }


}