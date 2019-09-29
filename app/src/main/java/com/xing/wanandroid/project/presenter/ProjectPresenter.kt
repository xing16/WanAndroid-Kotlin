package com.xing.wanandroid.project.presenter

import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.project.bean.ProjectTab
import com.xing.wanandroid.http.BaseObserver
import com.xing.wanandroid.project.contract.ProjectContract

class ProjectPresenter : BasePresenter<ProjectContract.View>(), ProjectContract.Presenter {

    override fun getProjectTabs() {
        addSubscribe(create(ApiService::class.java).getProjectTabs(), object : BaseObserver<List<ProjectTab>>() {
            override fun onSuccess(data: List<ProjectTab>?) {
                if (this@ProjectPresenter.isViewAttached()) {
                    this@ProjectPresenter.getView()?.onProjectTabs(data)
                }
            }
        })
    }
}