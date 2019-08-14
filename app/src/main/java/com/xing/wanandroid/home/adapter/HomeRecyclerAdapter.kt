package com.xing.wanandroid.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xing.wanandroid.R
import com.xing.wanandroid.home.bean.HomeRecommend
import com.xing.wanandroid.utils.format

class HomeRecyclerAdapter(layoutResId: Int) : BaseQuickAdapter<HomeRecommend, BaseViewHolder>(layoutResId) {


    override fun convert(helper: BaseViewHolder?, item: HomeRecommend?) {
        helper?.setText(R.id.tv_home_title, item?.title)
            ?.setText(R.id.tv_home_author, item?.author)
            ?.setText(R.id.tv_home_public_time, format(item!!.publishTime))
            ?.setText(R.id.tv_home_category, item?.superChapterName)
    }

}