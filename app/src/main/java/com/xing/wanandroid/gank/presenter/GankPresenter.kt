package com.xing.wanandroid.gank.presenter

import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.gank.bean.WxPublic
import com.xing.wanandroid.gank.contract.GankContract
import com.xing.wanandroid.http.BaseObserver

class GankPresenter : BasePresenter<GankContract.View>(), GankContract.Presenter {

    override fun getWxPublic() {
        addSubscribe(create(ApiService::class.java).getWxPublic(), object : BaseObserver<List<WxPublic>>() {
            override fun onSuccess(data: List<WxPublic>) {
                getView()?.onWxPublic(data)
            }
        })
    }
}