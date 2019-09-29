package com.xing.wanandroid.project.presenter

import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.http.BaseObserver
import com.xing.wanandroid.project.bean.ProjectResponse
import com.xing.wanandroid.project.contract.ProjectPageContract

class ProjectPagePresenter : BasePresenter<ProjectPageContract.View>(), ProjectPageContract.Presenter {
    override fun getProjectLists(page: Int, cid: Int) {
        addSubscribe(
            create(ApiService::class.java).getProjectLists(page, cid),
            object : BaseObserver<ProjectResponse>() {
                override fun onSuccess(data: ProjectResponse?) {
                    if (this@ProjectPagePresenter.isViewAttached()) {
                        this@ProjectPagePresenter.getView()?.onProjectLists(page, data)
                    }
                }
            })
    }
}