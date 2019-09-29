package com.xing.wanandroid.web.presenter

import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.BaseResponse
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.http.BaseObserver
import com.xing.wanandroid.web.bean.AddFavoriteResponse
import com.xing.wanandroid.web.contract.WebContract
import io.reactivex.Observable

class WebPresenter : BasePresenter<WebContract.View>(), WebContract.Presenter {

    override fun addFavorite(id: Int, title: String, author: String, link: String) {
        val observable: Observable<BaseResponse<AddFavoriteResponse>>
        if (id == -1) {
            observable = create(ApiService::class.java).addFavorite(title, author, link)
        } else {
            observable = create(ApiService::class.java).addFavorite(id)
        }
        addSubscribe(observable, object : BaseObserver<AddFavoriteResponse>() {
            override fun onSuccess(data: AddFavoriteResponse?) {
                getView()?.onAddFavorited(data)
            }
        })
    }

}