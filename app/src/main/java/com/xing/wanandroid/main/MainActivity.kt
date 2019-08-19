package com.xing.wanandroid.main

import android.app.Activity
import android.graphics.BitmapFactory
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.xing.wanandroid.R
import com.xing.wanandroid.adapter.MainViewPageAdapter
import com.xing.wanandroid.base.BaseActivity
import com.xing.wanandroid.bean.FragmentItem
import com.xing.wanandroid.home.HomeFragment
import com.xing.wanandroid.main.widgets.MainViewPager
import com.xing.wanandroid.meizi.MeiziActivity
import com.xing.wanandroid.project.ProjectFragment
import com.xing.wanandroid.project.ProjectPageFragment
import com.xing.wanandroid.search.SearchActivity
import com.xing.wanandroid.system.SystemFragment
import com.xing.wanandroid.utils.blur
import com.xing.wanandroid.utils.gotoActivity

class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mainMenu: ImageView
    private lateinit var mainSearch: ImageView
    private lateinit var mainTabLayout: TabLayout
    private lateinit var mainViewPager: MainViewPager
    private lateinit var mAdapter: MainViewPageAdapter
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var avatarBackground: ImageView

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        drawerLayout = findViewById(R.id.dl_drawer_layout)
        navigationView = findViewById(R.id.nv_left_navigation)
        val headerView: View = navigationView.getHeaderView(0)
        avatarBackground = headerView.findViewById(R.id.iv_avatar_background)
        mainMenu = findViewById(R.id.iv_main_menu)
        mainSearch = findViewById(R.id.iv_main_search)
        mainTabLayout = findViewById(R.id.tl_main_tab)
        mainViewPager = findViewById(R.id.vp_main_pager)

        mainMenu.setOnClickListener {
            openNavigationView()
        }

        navigationView.setNavigationItemSelectedListener { item ->
            Log.e("debug", "dcasdcasdc")
            when (item.itemId) {
                R.id.item_happy_minute -> {
                    gotoActivity(context as Activity, MeiziActivity().javaClass)
                    drawerLayout.closeDrawer(Gravity.LEFT)
                }
            }
            true
        }

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.dog)
        avatarBackground.setImageBitmap(blur(context, bitmap, 18))

    }

    private fun openNavigationView() {
        drawerLayout.openDrawer(Gravity.LEFT)
    }

    override fun initData() {
        val list = mutableListOf<FragmentItem>()
        list.add(FragmentItem("热门", HomeFragment.newInstance()))
        list.add(FragmentItem("项目", ProjectFragment.newInstance()))
        list.add(FragmentItem("体系", SystemFragment.newInstance()))
        list.add(FragmentItem("干货", ProjectPageFragment.newInstance(1)))
        mAdapter = MainViewPageAdapter(this, supportFragmentManager, list)
        mainViewPager.adapter = mAdapter
        mainTabLayout.setupWithViewPager(mainViewPager)

        for (i in 0..mainTabLayout.tabCount) {
            val tabView: TabLayout.Tab? = mainTabLayout.getTabAt(i)
            tabView?.customView = mAdapter.getTabView(i)
        }

        // 默认选中第 0 个
        mainViewPager.currentItem = 0
        changeTabView(mainTabLayout.getTabAt(0), 22f, true)

        mainTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                changeTabView(tab, 18f, false)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                changeTabView(tab, 22f, true)

            }
        })
        mainSearch.setOnClickListener(this)
    }

    private fun changeTabView(tab: TabLayout.Tab?, textSize: Float, isSelected: Boolean) {
        val view: View? = tab?.customView
        val textView: TextView? = view?.findViewById(R.id.tv_tab_title)
        textView?.textSize = textSize
        if (isSelected) {
            textView?.setTextColor(resources.getColor(android.R.color.black))
        } else {
            textView?.setTextColor(resources.getColor(R.color.colorGray))
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_main_search -> gotoSearchActivity()

        }
    }

    private fun gotoSearchActivity() {
        gotoActivity(this, SearchActivity().javaClass)
    }
}
