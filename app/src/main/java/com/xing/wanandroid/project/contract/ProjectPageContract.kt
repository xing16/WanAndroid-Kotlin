package com.xing.wanandroid.project.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.project.bean.ProjectResponse


interface ProjectPageContract {
    interface View : IView {
        fun onProjectLists(page: Int, response: ProjectResponse?)
    }

    interface Presenter {
        /**
         * 项目列表
         */
        fun getProjectLists(page: Int, cid: Int)

    }

}