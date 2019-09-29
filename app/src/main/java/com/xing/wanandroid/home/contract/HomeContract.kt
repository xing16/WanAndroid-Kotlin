package com.xing.wanandroid.project.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.home.bean.Banner
import com.xing.wanandroid.home.bean.Article


interface HomeContract {
    interface View : IView {

        fun onBanner(list: List<Banner>?)

        fun onArticles(page: Int, list: List<Article>?)
    }

    interface Presenter {

        fun getBanner()

        fun getArticles(page: Int)

    }

}