package com.xing.wanandroid.meizi.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.meizi.bean.Meizi

interface MeiziContract {
    interface View : IView {
        fun onMeiziList(page: Int, list: List<Meizi>?)
    }

    interface Presenter {
        fun getMeiziList(page: Int, pageSize: Int)
    }
}