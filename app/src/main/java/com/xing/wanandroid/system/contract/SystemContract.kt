package com.xing.wanandroid.system.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.system.bean.SystemCategory

interface SystemContract {

    interface View : IView {
        fun onSystemCategory(data: List<SystemCategory>?)
    }

    interface Presenter {
        /**
         * 获取系统分类
         */
        fun getSystemCategory()
    }

}