package com.xing.wanandroid.setting.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xing.wanandroid.R
import com.xing.wanandroid.gank.bean.WxPublic
import com.xing.wanandroid.home.bean.Article

class AboutLibraryAdapter : BaseQuickAdapter<String, BaseViewHolder> {

    constructor(layoutResId: Int) : super(layoutResId)

    constructor(layoutResId: Int, list: List<String>) : super(layoutResId, list)

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv_about_library_name, item)
    }
}