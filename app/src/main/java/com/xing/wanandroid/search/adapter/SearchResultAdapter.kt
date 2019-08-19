package com.xing.wanandroid.search.adapter

import android.text.TextUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xing.wanandroid.R
import com.xing.wanandroid.search.bean.SearchResult

class SearchResultAdapter(layoutRes: Int) : BaseQuickAdapter<SearchResult, BaseViewHolder>(layoutRes) {
    override fun convert(helper: BaseViewHolder?, item: SearchResult?) {
        helper?.setText(R.id.tv_search_result_title, item?.title)
            ?.setText(R.id.tv_search_result_author, item?.author)
            ?.setText(R.id.tv_search_result_date, item?.niceDate)
            ?.setGone(R.id.iv_search_result_image, !TextUtils.isEmpty(item?.envelopePic))
        Glide.with(mContext).load(item?.envelopePic).into(helper!!.getView(R.id.iv_search_result_image))
    }

}