package com.xing.wanandroid.main

import android.app.Activity
import android.graphics.BitmapFactory
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.jaeger.library.StatusBarUtil
import com.xing.wanandroid.R
import com.xing.wanandroid.main.adapter.MainViewPageAdapter
import com.xing.wanandroid.base.BaseActivity
import com.xing.wanandroid.bean.FragmentItem
import com.xing.wanandroid.favorite.FavoriteActivity
import com.xing.wanandroid.gank.GankFragment
import com.xing.wanandroid.home.HomeFragment
import com.xing.wanandroid.main.widgets.MainViewPager
import com.xing.wanandroid.meizi.MeiziActivity
import com.xing.wanandroid.project.ProjectFragment
import com.xing.wanandroid.search.SearchActivity
import com.xing.wanandroid.setting.SettingActivity
import com.xing.wanandroid.system.SystemFragment
import com.xing.wanandroid.user.activity.LoginActivity
import com.xing.wanandroid.utils.blur
import com.xing.wanandroid.utils.gotoActivity
import kotlin.system.exitProcess

class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mainMenu: ImageView
    private lateinit var mainSearch: ImageView
    private lateinit var mainTabLayout: TabLayout
    private lateinit var mainViewPager: MainViewPager
    private lateinit var mAdapter: MainViewPageAdapter
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var avatarBackground: ImageView
    private lateinit var usernameTextView: TextView

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        drawerLayout = findViewById(R.id.dl_drawer_layout)
        StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, resources.getColor(R.color.colorPrimary), 0)
        navigationView = findViewById(R.id.nv_left_navigation)
        val headerView: View = navigationView.getHeaderView(0)
        usernameTextView = headerView.findViewById(R.id.tv_nav_username)
        avatarBackground = headerView.findViewById(R.id.iv_avatar_background)
        mainMenu = findViewById(R.id.iv_main_menu)
        mainSearch = findViewById(R.id.iv_main_search)
        mainTabLayout = findViewById(R.id.tl_main_tab)
        mainViewPager = findViewById(R.id.vp_main_pager)

        mainMenu.setOnClickListener {
            openDrawer()
        }
        usernameTextView.setOnClickListener(this)

        navigationView.setNavigationItemSelectedListener { item ->
            closeDrawer()
            when (item.itemId) {
                R.id.item_nav_happy_minute -> {
                    gotoActivity(mContext as Activity, MeiziActivity().javaClass)

                }
                R.id.item_nav_favorite -> {
                    gotoActivity(mContext as Activity, FavoriteActivity().javaClass)
                }
                R.id.item_nav_setting -> {
                    gotoActivity(mContext as Activity, SettingActivity().javaClass)
                }
            }
            true
        }

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar)
        avatarBackground.setImageBitmap(blur(mContext, bitmap, 22))

    }

    override fun initData() {
        val list = mutableListOf<FragmentItem>()
        list.add(FragmentItem("首页", HomeFragment.newInstance()))
        list.add(FragmentItem("项目", ProjectFragment.newInstance()))
        list.add(FragmentItem("体系", SystemFragment.newInstance()))
        list.add(FragmentItem("干货", GankFragment.newInstance()))
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
        if (true) {
            usernameTextView.text = getString(R.string.click_to_login)
        }

        mainSearch.setOnClickListener(this)
    }

    /**
     * 打开抽屉
     */
    private fun openDrawer() {
        drawerLayout.openDrawer(Gravity.START)
    }

    /**
     * 关闭抽屉
     */
    private fun closeDrawer() {
        drawerLayout.closeDrawer(Gravity.START)
    }

    private fun changeTabView(tab: TabLayout.Tab?, textSize: Float, isSelected: Boolean) {
        val view: View? = tab?.customView
        val textView: TextView? = view?.findViewById(R.id.tv_tab_title)
        textView?.textSize = textSize
        if (isSelected) {
            textView?.setTextColor(resources.getColor(android.R.color.black))
            val width = textView?.measuredWidth
            Log.e("debug", "width = $width")
        } else {
            textView?.setTextColor(resources.getColor(R.color.gray_959698))
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_main_search -> {
                gotoSearchActivity()
                overridePendingTransition(0, 0)
            }
            R.id.tv_nav_username -> {
                gotoLoginActivity()
                closeDrawer()
            }
        }
    }

    private fun gotoSearchActivity() {
        gotoActivity(this, SearchActivity().javaClass)
    }

    private fun gotoLoginActivity() {
        gotoActivity(this, LoginActivity().javaClass)
    }

    private var lastTime: Long = 0L

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val now = System.currentTimeMillis()
            if (now - lastTime > 1000) {
                Toast.makeText(mContext, "再按一次,推出应用", Toast.LENGTH_LONG).show()
                lastTime = now
                return false
            }
            finish()
            exitProcess(0)
        }
        return super.onKeyDown(keyCode, event)
    }
}
