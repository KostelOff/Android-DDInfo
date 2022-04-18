package com.ntechnology.ddinfo.models

data class GenderGradesModel(val id: String, val name: String, val items: List<GenderGradesSelectionModel>)

data class GenderGradesSelectionModel(val title: String, var value: List<List<String>>)

data class SelectedGenderGrade(val title: String, var value: String="")

data class GenderGradeStateModel(val gradeState: MutableMap<Int, SelectedGenderGrade>)

