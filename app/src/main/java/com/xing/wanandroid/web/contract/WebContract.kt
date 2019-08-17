package com.xing.wanandroid.web.contract

import com.xing.wanandroid.base.mvp.IView

interface WebContract {
    interface View : IView {

    }

    interface Presenter {
        fun addFavorite()
    }
}