package com.xing.wanandroid.system.bean

data class SystemCategory(
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    val children: ArrayList<SystemCategory>
)