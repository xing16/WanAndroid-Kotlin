package com.xing.wanandroid.gank.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.gank.bean.WxPublic

interface GankContract {
    interface View : IView {
        fun onWxPublic(list: List<WxPublic>)
    }

    interface Presenter {
        // 公众号
        fun getWxPublic()
    }
}