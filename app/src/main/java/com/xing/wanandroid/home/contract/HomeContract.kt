package com.xing.wanandroid.project.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.home.bean.Banner
import com.xing.wanandroid.home.bean.HomeResponse


interface HomeContract {
    interface View : IView {

        fun onBanner(list: List<Banner>)

        fun onRecommend(response:HomeResponse)
    }

    interface Presenter {

        fun getBanner()

        fun getRecommend(page: Int)


    }

}