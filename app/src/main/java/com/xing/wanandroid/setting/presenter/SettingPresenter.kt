package com.xing.wanandroid.setting.presenter

import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.http.BaseObserver
import com.xing.wanandroid.setting.bean.LogoutResult
import com.xing.wanandroid.setting.contract.SettingContract

class SettingPresenter : BasePresenter<SettingContract.View>(), SettingContract.Presenter {

    override fun logout() {
        addSubscribe(create(ApiService::class.java).logout(), object : BaseObserver<LogoutResult>() {
            override fun onSuccess(result: LogoutResult?) {
                getView()?.onLogoutResult()
            }
        })
    }
}