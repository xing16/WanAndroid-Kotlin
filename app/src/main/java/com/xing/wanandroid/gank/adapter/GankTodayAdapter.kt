package com.xing.wanandroid.gank.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xing.wanandroid.R
import com.xing.wanandroid.gank.bean.GankToday
import com.xing.wanandroid.gank.bean.GankTodayEntity

class GankTodayAdapter(layoutResId: Int, sectionHeadResId: Int, list: List<GankTodayEntity<GankToday>>) : BaseSectionQuickAdapter<GankTodayEntity<GankToday>, BaseViewHolder>(layoutResId, sectionHeadResId, list) {

    override fun convertHead(helper: BaseViewHolder?, item: GankTodayEntity<GankToday>?) {
        helper?.setText(R.id.tv_gank_section_head, item?.header)
    }

    override fun convert(helper: BaseViewHolder?, item: GankTodayEntity<GankToday>?) {
        val publishedAt: String? = item?.t?.publishedAt
        var publishedAtString: String
        when {
            publishedAt == null -> publishedAtString = ""
            publishedAt.length < 10 -> publishedAtString = publishedAt
            else -> publishedAtString = publishedAt.substring(0, 10)
        }
        helper?.setText(R.id.tv_gank_desc, item?.t?.desc)
            ?.setText(R.id.tv_gank_who, item?.t?.who)
            ?.setText(R.id.tv_gank_create_time, publishedAtString)
            ?.setGone(R.id.ll_gank_images_parent, item?.t?.images != null && item.t?.images?.size != 0)

        if (item?.t?.images?.size ?: 0 > 2) {
            Glide.with(mContext).load(item?.t?.images?.get(0)).into(helper?.getView(R.id.iv_gank_left)!!)
            Glide.with(mContext).load(item?.t?.images?.get(1)).into(helper?.getView(R.id.iv_gank_center)!!)
            Glide.with(mContext).load(item?.t?.images?.get(2)).into(helper?.getView(R.id.iv_gank_right)!!)
        } else if (item?.t?.images?.size ?: 0 == 1) {
            Glide.with(mContext).load(item?.t?.images?.get(0)).into(helper?.getView(R.id.iv_gank_left)!!)
            helper?.setGone(R.id.iv_gank_center, false)
            helper?.setGone(R.id.iv_gank_right, false)
        }


    }

}