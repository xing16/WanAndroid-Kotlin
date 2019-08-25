package com.xing.wanandroid.home.bean

data class Article(
    val id: Int,
    val title: String,
    val author: String,
    val publishTime: Long,
    val superChapterName: String,
    val envelopePic: String,
    val link: String,
    val desc: String,
    val fresh: Boolean,
    val collect: Boolean,
    val type: Int
)