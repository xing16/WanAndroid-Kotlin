package com.xing.wanandroid.search.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.search.bean.SearchResultResponse

interface SearchResultContract {
    interface View : IView {
        fun onSearchResult(page: Int, response: SearchResultResponse?)
    }

    interface Presenter {
        fun getSearchResult(page: Int, keyword: String?)
    }
}