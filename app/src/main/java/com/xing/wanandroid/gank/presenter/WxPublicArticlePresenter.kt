package com.xing.wanandroid.gank.presenter

import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.gank.contract.WxPublicArticleContract
import com.xing.wanandroid.home.bean.Article
import com.xing.wanandroid.home.bean.ArticleResponse
import com.xing.wanandroid.http.BaseObserver
import java.io.File
import javax.xml.transform.Templates

class WxPublicArticlePresenter : BasePresenter<WxPublicArticleContract.View>(), WxPublicArticleContract.Presenter {
    override fun getWxPublicArticle(id: Int, page: Int) {
        addSubscribe(create(ApiService::class.java).getWxPublicArticle(id, page), object : BaseObserver<ArticleResponse>() {
            override fun onSuccess(data: ArticleResponse?) {
                getView()?.onWxPublicArticle(page, data?.datas)
            }
        })
    }

}