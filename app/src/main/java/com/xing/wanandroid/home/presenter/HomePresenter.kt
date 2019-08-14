package com.xing.wanandroid.project.presenter

import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.home.bean.Banner
import com.xing.wanandroid.home.bean.HomeRecommend
import com.xing.wanandroid.home.bean.HomeResponse
import com.xing.wanandroid.http.BaseObserver
import com.xing.wanandroid.project.contract.HomeContract

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    override fun getBanner() {
        addSubscribe(create(ApiService::class.java).getBanner(), object : BaseObserver<List<Banner>>() {
            override fun onSuccess(data: List<Banner>) {
                getView()?.onBanner(data)
            }
        })
    }

    override fun getRecommend(page: Int) {
        addSubscribe(create(ApiService::class.java).getHomeRecommend(page),
            object : BaseObserver<HomeResponse>() {
                override fun onSuccess(response: HomeResponse) {
                    getView()?.onRecommend(response)
                }
            })
    }
}