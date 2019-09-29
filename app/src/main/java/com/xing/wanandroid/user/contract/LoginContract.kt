package com.xing.wanandroid.user.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.db.bean.User
import com.xing.wanandroid.user.bean.LoginResponse

interface LoginContract {

    interface View : IView {
        fun onLoginResult(username: String, user: User?)
    }

    interface Presenter {
        fun login(username: String, password: String)
    }
}
