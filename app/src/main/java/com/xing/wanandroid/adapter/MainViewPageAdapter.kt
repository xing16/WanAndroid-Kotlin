package com.xing.wanandroid.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xing.wanandroid.R
import com.xing.wanandroid.bean.FragmentItem

class MainViewPageAdapter(var context: Context, fm: FragmentManager, var fragmentItems: List<FragmentItem>) :
    FragmentPagerAdapter(fm) {

    private var layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentItems[position].fragment
    }

    override fun getCount(): Int {
        return fragmentItems.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentItems[position].title
    }

    fun setDataSource(list: List<FragmentItem>) {
        fragmentItems = list
        notifyDataSetChanged()
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }

    fun getTabView(position: Int): View {
        var view: View = layoutInflater.inflate(R.layout.layout_main_tab, null, false)
        var textView: TextView = view.findViewById(R.id.tv_tab_title)
        textView.text = getPageTitle(position)
        textView.setTextColor(context.resources.getColor(R.color.colorGray))
        textView.textSize = 18f
        return view
    }


}