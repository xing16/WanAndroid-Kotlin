package com.xing.wanandroid.web.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.web.bean.AddFavoriteResponse

interface WebContract {
    interface View : IView {
        fun onAddFavorited(addFavoriteResponse: AddFavoriteResponse?)
    }

    interface Presenter {
        fun addFavorite(id: Int, title: String, author: String, link: String)
    }
}