package com.xing.wanandroid.favorite.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xing.wanandroid.R
import com.xing.wanandroid.home.bean.Article
import com.xing.wanandroid.utils.format

class FavoriteAdapter(layoutResId: Int) : BaseQuickAdapter<Article, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder?, item: Article?) {
        helper?.setText(R.id.tv_home_title, item?.title)
            ?.setText(R.id.tv_home_author, item?.author)
            ?.setText(R.id.tv_home_public_time, format(item?.publishTime ?: System.currentTimeMillis()))
            ?.setText(R.id.tv_home_category, item?.superChapterName)
            ?.setGone(R.id.tv_home_recent, false)   // true 显示，false 隐藏
    }

}