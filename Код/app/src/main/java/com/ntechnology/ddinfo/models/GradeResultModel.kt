package com.ntechnology.ddinfo.models

data class GradeResultModel(val title: String, val value: String)

data class GradeStateModel(val gradeState: MutableMap<String, MutableList<String>>)