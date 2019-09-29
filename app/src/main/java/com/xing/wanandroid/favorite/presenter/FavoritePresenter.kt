package com.xing.wanandroid.favorite.presenter

import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.favorite.contract.FavoriteContract
import com.xing.wanandroid.home.bean.ArticleResponse
import com.xing.wanandroid.http.BaseObserver

class FavoritePresenter : BasePresenter<FavoriteContract.View>(), FavoriteContract.Presenter {

    override fun getArticleFavorites(page: Int) {
        addSubscribe(create(ApiService::class.java).getArticleFavorites(page), object : BaseObserver<ArticleResponse>() {
            override fun onSuccess(response: ArticleResponse?) {
                getView()?.onArticleFavorite(page, response)
            }
        })
    }
}