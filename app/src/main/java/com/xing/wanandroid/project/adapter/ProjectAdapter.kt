package com.xing.wanandroid.project.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xing.wanandroid.R
import com.xing.wanandroid.project.bean.Project

class ProjectAdapter(layoutId: Int) : BaseQuickAdapter<Project, BaseViewHolder>(layoutId) {

    override fun convert(helper: BaseViewHolder?, item: Project?) {
        helper?.setText(R.id.tv_project_title, item?.title)
            ?.setText(R.id.tv_project_author, item?.author)
            ?.setText(R.id.tv_project_time, item?.niceDate)
            ?.addOnClickListener(R.id.iv_project_image)    // 设置子 view 可以响应点击事件

        Glide.with(mContext).load(item?.envelopePic).into(helper!!.getView(R.id.iv_project_image))
    }
}