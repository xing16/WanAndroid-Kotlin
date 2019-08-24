package com.xing.wanandroid.image.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.chrisbanes.photoview.PhotoView
import com.xing.wanandroid.R

class ImageBrowseAdapter(layoutResId: Int) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        val photoView: PhotoView? = helper?.getView(R.id.pv_image)
        Glide.with(mContext).load(item ?: "").into(photoView!!)
    }
}