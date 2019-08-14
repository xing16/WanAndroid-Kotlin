package com.xing.wanandroid.system.presenter

import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.system.contract.SystemContract

class SystemPresenter : BasePresenter<SystemContract.View>(), SystemContract.Presenter {

    override fun getSystem() {
    }
}