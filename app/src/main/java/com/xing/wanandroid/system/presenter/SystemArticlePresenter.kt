package com.xing.wanandroid.system.presenter

import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.home.bean.ArticleResponse
import com.xing.wanandroid.http.BaseObserver
import com.xing.wanandroid.system.contract.SystemArticleContract

class SystemArticlePresenter : BasePresenter<SystemArticleContract.View>(), SystemArticleContract.Presenter {

    override fun getSystemArticles(page: Int, cid: Int) {
        addSubscribe(create(ApiService::class.java).getSystemArticles(page, cid),
            object : BaseObserver<ArticleResponse>() {
                override fun onSuccess(response: ArticleResponse?) {
                    getView()?.onSystemArticles(page, response?.datas)
                }
            })
    }
}