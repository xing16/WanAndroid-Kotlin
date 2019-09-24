package com.xing.wanandroid.setting.contract

import com.xing.wanandroid.base.mvp.IView

interface SettingContract {

    interface View : IView {
        fun onLogoutResult()
    }

    interface Presenter {
        fun logout()
    }
}