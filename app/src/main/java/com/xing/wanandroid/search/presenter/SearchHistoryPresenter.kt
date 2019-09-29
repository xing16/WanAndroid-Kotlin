package com.xing.wanandroid.search.presenter

import android.util.Log
import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.db.DbManager
import com.xing.wanandroid.http.BaseObserver
import com.xing.wanandroid.search.bean.SearchHot
import com.xing.wanandroid.search.contract.SearchHistoryContract
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

class SearchHistoryPresenter : BasePresenter<SearchHistoryContract.View>(), SearchHistoryContract.Presenter {

    /**
     * 搜索热门
     */
    override fun getSearchHot() {
        addSubscribe(create(ApiService::class.java).getSearchHot(), object : BaseObserver<ArrayList<SearchHot>>() {
            override fun onSuccess(data: ArrayList<SearchHot>?) {
                if (this@SearchHistoryPresenter.isViewAttached()) {
                    Log.e("debug", "getSearchHot() = " + data?.size)
                    this@SearchHistoryPresenter.getView()?.onSearchHot(data)
                }
            }
        })
    }

    override fun addSearchHistory(keyword: String) {

    }


    /**
     * 搜索历史
     */
    override fun getSearchHistory() {
        Observable.create(object : ObservableOnSubscribe<Any> {
            override fun subscribe(emitter: ObservableEmitter<Any>) {

//                emitter.onNext()
            }
        })


    }

}