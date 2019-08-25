package com.xing.wanandroid.user.presenter

import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.http.BaseObserver
import com.xing.wanandroid.user.bean.LoginResponse
import com.xing.wanandroid.user.contract.LoginContract

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun login(username: String, password: String) {
        addSubscribe(create(ApiService::class.java).login(username, password), object : BaseObserver<LoginResponse>() {
            override fun onSuccess(data: LoginResponse) {
                getView()?.onLoginResult(data)
            }
        })
    }
}