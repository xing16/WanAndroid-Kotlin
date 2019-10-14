package com.xing.wanandroid.system.adapter

import android.graphics.Typeface
import android.util.Log
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xing.wanandroid.R
import com.xing.wanandroid.system.bean.SystemCategory

class SystemCategoryAdapter(layoutResId: Int) : BaseQuickAdapter<SystemCategory, BaseViewHolder>(layoutResId) {

    private var mClickedPosition: Int = 0

    override fun convert(helper: BaseViewHolder?, item: SystemCategory?) {
        val position = helper?.layoutPosition
        if (position == mClickedPosition) {
            helper.setTextColor(R.id.tv_system_category, ContextCompat.getColor(mContext, R.color.colorAccent))
            helper.setVisible(R.id.view_indicator, true)
            // 加粗体
            helper.setTypeface(R.id.tv_system_category, Typeface.defaultFromStyle(Typeface.BOLD))
        } else {
            helper?.setTextColor(R.id.tv_system_category, ContextCompat.getColor(mContext, R.color.black_f222))
            helper?.setVisible(R.id.view_indicator, false)
            // 取消粗体
            helper?.setTypeface(R.id.tv_system_category, Typeface.defaultFromStyle(Typeface.NORMAL))
        }
        helper?.setText(R.id.tv_system_category, item?.name)
    }

    fun setClickedPosition(position: Int) {
        this.mClickedPosition = position
        notifyDataSetChanged()
    }
}


