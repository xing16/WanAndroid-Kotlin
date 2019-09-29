package com.xing.wanandroid.system.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.home.bean.Article

interface SystemArticleContract {

    interface View : IView {
        fun onSystemArticles(page: Int, list: List<Article>?)
    }

    interface Presenter {
        fun getSystemArticles(page: Int, cid: Int)
    }

}