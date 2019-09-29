package com.xing.wanandroid.favorite.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.home.bean.ArticleResponse

interface FavoriteContract {

    interface View : IView {
        fun onArticleFavorite(page: Int, response: ArticleResponse?)
    }

    interface Presenter {
        fun getArticleFavorites(page: Int)
    }
}