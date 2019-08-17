package com.xing.wanandroid.web.presenter

import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.web.contract.WebContract

class WebPresenter : BasePresenter<WebContract.View>() , WebContract.Presenter {
    override fun addFavorite() {
    }

}