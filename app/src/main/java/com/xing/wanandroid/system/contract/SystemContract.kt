package com.xing.wanandroid.system.contract

import com.xing.wanandroid.base.mvp.IView

interface SystemContract {

    interface View : IView {
    }

    interface Presenter {
        fun getSystem()
    }


}