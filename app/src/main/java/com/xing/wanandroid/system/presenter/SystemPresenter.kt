package com.xing.wanandroid.system.presenter

import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.http.BaseObserver
import com.xing.wanandroid.system.bean.SystemCategory
import com.xing.wanandroid.system.contract.SystemContract

class SystemPresenter : BasePresenter<SystemContract.View>(), SystemContract.Presenter {

    override fun getSystemCategory() {
        addSubscribe(create(ApiService::class.java).getSystem(), object : BaseObserver<List<SystemCategory>>() {
            override fun onSuccess(data: List<SystemCategory>?) {
                getView()?.onSystemCategory(data)
            }
        })
    }
}