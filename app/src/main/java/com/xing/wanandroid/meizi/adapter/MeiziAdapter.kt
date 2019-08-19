package com.xing.wanandroid.meizi.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xing.wanandroid.R
import com.xing.wanandroid.meizi.bean.Meizi
import com.xing.wanandroid.widget.RoundImageView

class MeiziAdapter(layoutResId: Int) : BaseQuickAdapter<Meizi, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder?, item: Meizi?) {
        val imageView: RoundImageView? = helper?.getView(R.id.iv_meizi)
        Glide.with(mContext).load(item?.url).into(imageView!!)
    }
}