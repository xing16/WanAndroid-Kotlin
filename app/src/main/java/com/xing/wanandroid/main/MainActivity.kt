package com.xing.wanandroid.main

import android.app.Activity
import android.graphics.BitmapFactory
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.jaeger.library.StatusBarUtil
import com.xing.wanandroid.R
import com.xing.wanandroid.base.mvp.BaseMVPActivity
import com.xing.wanandroid.common.annotation.EventBusSubscribe
import com.xing.wanandroid.common.bean.FragmentItem
import com.xing.wanandroid.db.DbManager
import com.xing.wanandroid.db.bean.User
import com.xing.wanandroid.favorite.FavoriteActivity
import com.xing.wanandroid.gank.GankFragment
import com.xing.wanandroid.home.HomeFragment
import com.xing.wanandroid.main.adapter.MainViewPageAdapter
import com.xing.wanandroid.main.bean.LoggedInEvent
import com.xing.wanandroid.main.contract.MainContract
import com.xing.wanandroid.main.presenter.MainPresenter
import com.xing.wanandroid.main.widgets.MainViewPager
import com.xing.wanandroid.meizi.MeiziActivity
import com.xing.wanandroid.project.ProjectFragment
import com.xing.wanandroid.search.SearchActivity
import com.xing.wanandroid.setting.AboutActivity
import com.xing.wanandroid.setting.SettingActivity
import com.xing.wanandroid.system.SystemFragment
import com.xing.wanandroid.user.activity.LoginActivity
import com.xing.wanandroid.utils.blur
import com.xing.wanandroid.utils.gotoActivity
import com.xing.wanandroid.utils.isCookieNotEmpty
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.system.exitProcess

@EventBusSubscribe
class MainActivity : BaseMVPActivity<MainContract.View, MainPresenter>(), MainContract.View, View.OnClickListener {

    private lateinit var mainMenu: ImageView
    private lateinit var mainSearch: ImageView
    private lateinit var mainTabLayout: TabLayout
    private lateinit var mainViewPager: MainViewPager
    private lateinit var mAdapter: MainViewPageAdapter
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var avatarBackground: ImageView
    private lateinit var usernameTextView: TextView
    private var loggedIn = false

    override fun createPresenter(): MainPresenter {
        return MainPresenter()
    }

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
                R.id.item_nav_about -> {
                    gotoActivity(mContext as Activity, AboutActivity().javaClass)
                }
            }
            true
        }

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar)
        avatarBackground.setImageBitmap(blur(mContext, bitmap, 22))
    }

    override fun initData() {
        super.initData()
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
        setUsernameFromCache()
        presenter.getUserInfo()
        mainSearch.setOnClickListener(this)
    }

    private fun setUsernameFromCache() {
        loggedIn = isCookieNotEmpty(mContext)
        if (!loggedIn) {
            usernameTextView.text = getString(R.string.click_to_login)
        } else {
            val user = getCacheUser()
            val username: String
            if (user != null) {
                username = user.username
            } else {
                username = ""
            }
            usernameTextView.text = username
        }
    }

    /**
     * 设置用户名称，头像等信息
     */
    private fun setUsername(user: User?) {
        if (user != null) {
            usernameTextView.text = user.username
        } else {
            usernameTextView.text = getString(R.string.click_to_login)
        }
    }

    private fun getCacheUser(): User? {
        val users = DbManager.getInstance().getUserDao().loadAll()
        if (users != null && users.size > 0) {
            return users[0]
        }
        return null
    }

    override fun onUserInfo(user: User) {
        Log.e("MainActivity", user.toString())
        loggedIn = isCookieNotEmpty(mContext)
        if (loggedIn) {
            usernameTextView.text = user.username
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginStatusChanged(event: LoggedInEvent) {
        val user = event.user
        setUsername(user)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
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
                loggedIn = isCookieNotEmpty(mContext)
                if (!loggedIn) {
                    gotoLoginActivity()
                    closeDrawer()
                }
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

    override fun onDestroy() {
        super.onDestroy()
    }
}
