package com.xing.wanandroid.meizi.presenter

import android.util.Log
import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.http.BaseObserver
import com.xing.wanandroid.meizi.bean.Meizi
import com.xing.wanandroid.meizi.contract.MeiziContract

class MeiziPresenter : BasePresenter<MeiziContract.View>(), MeiziContract.Presenter {
    override fun getMeiziList(page: Int, pageSize: Int) {
        addSubscribe(
            create(ApiService::class.java).getMeiziList(pageSize, page),
            object : BaseObserver<List<Meizi>>() {
                override fun onSuccess(data: List<Meizi>?) {
                    Log.e("debug", "data = ${data?.size}")
                    if (this@MeiziPresenter.isViewAttached()) {
                        this@MeiziPresenter.getView()?.onMeiziList(page, data)
                    }
                }
            })
    }

}