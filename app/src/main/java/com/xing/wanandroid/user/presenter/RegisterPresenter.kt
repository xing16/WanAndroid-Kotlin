package com.xing.wanandroid.user.presenter

import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.http.BaseObserver
import com.xing.wanandroid.user.bean.RegisterResponse
import com.xing.wanandroid.user.contract.RegisterContract

class RegisterPresenter : BasePresenter<RegisterContract.View>(), RegisterContract.Presenter {

    override fun register(username: String, password: String, repassword: String) {
        addSubscribe(create(ApiService::class.java).register(username, password, repassword),
            object : BaseObserver<RegisterResponse>() {
                override fun onSuccess(data: RegisterResponse?) {
                    getView()?.onRegisterResult(data)
                }
            })
    }
}