package com.xing.wanandroid.gank.presenter

import android.util.Log
import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.gank.bean.GankToday
import com.xing.wanandroid.gank.bean.WxPublic
import com.xing.wanandroid.gank.contract.GankContract
import com.xing.wanandroid.http.BaseObserver

class GankPresenter : BasePresenter<GankContract.View>(), GankContract.Presenter {

    override fun getGankToday() {
        addSubscribe(create(ApiService::class.java).getGankToday(), object : BaseObserver<HashMap<String, List<GankToday>>>() {
            override fun onSuccess(map: HashMap<String, List<GankToday>>?) {
                Log.e("debug", "map = $map")
                getView()?.onGankToday(map)
            }
        })
    }

    override fun getWxPublic() {
        addSubscribe(create(ApiService::class.java).getWxPublic(), object : BaseObserver<List<WxPublic>>() {
            override fun onSuccess(data: List<WxPublic>?) {
                getView()?.onWxPublic(data)
            }
        })
    }
}