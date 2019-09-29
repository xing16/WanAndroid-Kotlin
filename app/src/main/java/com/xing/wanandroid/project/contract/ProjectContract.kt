package com.xing.wanandroid.project.contract

import com.xing.wanandroid.base.mvp.IView
import com.xing.wanandroid.project.bean.ProjectTab


interface ProjectContract {
    interface View : IView {
        fun onProjectTabs(projectTabs: List<ProjectTab>?)
    }

    interface Presenter {
        fun getProjectTabs()
    }

}