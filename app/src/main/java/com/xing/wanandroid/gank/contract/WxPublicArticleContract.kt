package com.xing.wanandroid.gank.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.home.bean.Article

interface WxPublicArticleContract {

    interface View : IView {
        fun onWxPublicArticle(page: Int, list: List<Article>?)
    }

    interface Presenter {
        // 公众号
        fun getWxPublicArticle(id: Int, page: Int)
    }
}