package com.xing.wanandroid.search.bean

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id

@Entity
class SearchHistory {

    @Id
    private lateinit var keyword: String

    private var createTime: Long = 0




}