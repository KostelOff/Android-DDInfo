package com.ntechnology.ddinfo.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ntechnology.ddinfo.models.GradesSelectionItem
import com.ntechnology.ddinfo.models.GradesSelectionViewItem
import com.ntechnology.ddinfo.ui.views.GradesView
import com.ntechnology.ddinfo.utils.GradeItemsProvider
import com.ntechnology.ddinfo.utils.GradeStorage
import com.ntechnology.ddinfo.utils.GradesProvider
import javax.inject.Inject

@InjectViewState
class GradesPresenter @Inject constructor(
    private val gradesProvider: GradesProvider,
    private val gradeStorage: GradeStorage,
    private val gradeItemsProvider: GradeItemsProvider) : MvpPresenter<GradesView>() {

    private val grades by lazy {
        gradeItemsProvider.readGrade(id)
    }

    private var selectionMap = mutableMapOf<String, MutableList<String>>()

    private var lastClickedPosition = -1

    var id = ""

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        grades?.let { grades ->
            gradeStorage.getGradeState(grades)?.let { state ->
                selectionMap = state
            }

            viewState.setGrades(grades)
            setGradesViews()

        }
    }


    override fun detachView(view: GradesView?) {
        grades?.let {
            gradeStorage.saveGradeState(it, selectionMap)
        }
        super.detachView(view)
    }

    fun onGradeItemClick(item: GradesSelectionViewItem, selection: String?) {
        lastClickedPosition = getSelectionList(item.item).indexOf(selection)
        viewState.showSelectionScreen(item.item, selection)
    }

    fun onClearClick() {
        selectionMap.clear()
        setGradesViews()
    }

    fun onSelectionChoose(item: GradesSelectionItem, selection: String?) {
        val previousSelection = getLastClickedSelection(item)

        if (previousSelection == null && selection == null) {
            return
        }
        if (previousSelection == null && selection != null) {
            getSelectionList(item).add(selection)
        }
        if (previousSelection != null && selection == null) {
            getSelectionList(item).remove(previousSelection)
        }
        if (previousSelection != null && selection != null && lastClickedPosition >= 0) {
            getSelectionList(item)[lastClickedPosition] = selection
        }

        setGradesViews()
    }

    private fun getSelectionList(item: GradesSelectionItem): MutableList<String> {
        val key = item.title
        if (!selectionMap.containsKey(key)) {
            selectionMap[key] = mutableListOf()
        }
        return selectionMap[key] ?: mutableListOf()
    }

    private fun getLastClickedSelection(item: GradesSelectionItem): String? {
        var selection: String? = null
        if (lastClickedPosition >= 0) {
            selection = getSelectionList(item)[lastClickedPosition]
        }
        return selection
    }

    private fun setGradesViews() {
        viewState.setGradesItems(getGradeViewItems())
        grades?.let {

            val results = gradesProvider.getGrade(it, selectionMap)
            viewState.setGradesResult(if (allFieldsSelected()) results else null)

            if (results == null && allFieldsSelected()) {
                viewState.showError()
            }
        }
    }

    private fun allFieldsSelected(): Boolean {
        if (selectionMap.size < 3) return false

        for (item in selectionMap.values) {
            if (item.isEmpty()) return false
        }
        return true
    }

    private fun getGradeViewItems(): List<GradesSelectionViewItem> {
        return grades?.items?.map { GradesSelectionViewItem(it, selectionMap[it.title].orEmpty()) }.orEmpty()
    }


}