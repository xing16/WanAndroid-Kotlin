package com.xing.wanandroid.search.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.search.bean.SearchHot

interface SearchHistoryContract {
    interface View : IView {

        fun onSearchHot(searchHots: ArrayList<SearchHot>)

        fun onSearchHistory()
    }

    interface Presenter {

        fun getSearchHot()

        fun getSearchHistory()

    }
}