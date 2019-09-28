package com.xing.wanandroid.main.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.db.bean.User

interface MainContract {

    interface View : IView {
        fun onUserInfo(user: User)
    }

    interface Presenter {
        fun getUserInfo()
    }
}