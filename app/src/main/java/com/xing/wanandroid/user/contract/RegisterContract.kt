package com.xing.wanandroid.user.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.user.bean.RegisterResponse

interface RegisterContract {

    interface View : IView {
        fun onRegisterResult(result: RegisterResponse?)
    }

    interface Presenter {
        fun register(username: String, password: String, repassword: String)
    }
}