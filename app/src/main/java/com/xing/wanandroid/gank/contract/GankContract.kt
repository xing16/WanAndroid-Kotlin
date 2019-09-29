package com.xing.wanandroid.gank.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.gank.bean.GankToday
import com.xing.wanandroid.gank.bean.WxPublic

interface GankContract {
    interface View : IView {
        fun onWxPublic(list: List<WxPublic>?)

        fun onGankToday(map: HashMap<String, List<GankToday>>?)
    }

    interface Presenter {
        // 公众号
        fun getWxPublic()

        // 最新一天的干货
        fun getGankToday()
    }
}