package com.xing.wanandroid.project.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xing.wanandroid.common.bean.FragmentItem

class ProjectPageAdapter(fm: FragmentManager, var fragmentItems: List<FragmentItem>) : FragmentPagerAdapter(fm) {

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

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }


//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//    }


}