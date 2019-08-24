package com.xing.wanandroid.project.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.home.bean.Banner
import com.xing.wanandroid.home.bean.HomeArticle
import com.xing.wanandroid.home.bean.HomeResponse


interface HomeContract {
    interface View : IView {

        fun onBanner(list: List<Banner>)

        fun onArticles(page: Int, list: List<HomeArticle>)
    }

    interface Presenter {

        fun getBanner()

        fun getArticles(page: Int)

    }

}