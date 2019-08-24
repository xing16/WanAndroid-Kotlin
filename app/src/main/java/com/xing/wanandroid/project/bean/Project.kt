package com.xing.wanandroid.project.bean

data class Project(
    var id: Int,
    var collect: Boolean,
    var author: String,
    var desc: String,
    var envelopePic: String,
    var fresh: Boolean,
    var type: Int,
    var link: String,
    var niceDate: String,
    var title: String
)