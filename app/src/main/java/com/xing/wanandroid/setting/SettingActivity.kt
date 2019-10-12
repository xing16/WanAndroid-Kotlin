package com.xing.wanandroid.setting

import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.xing.wanandroid.R
import com.xing.wanandroid.app.MainApp
import com.xing.wanandroid.base.mvp.BaseMVPActivity
import com.xing.wanandroid.main.bean.LoggedInEvent
import com.xing.wanandroid.setting.contract.SettingContract
import com.xing.wanandroid.setting.presenter.SettingPresenter
import com.xing.wanandroid.utils.dp2px
import com.xing.wanandroid.utils.isCookieNotEmpty
import org.greenrobot.eventbus.EventBus

class SettingActivity : BaseMVPActivity<SettingContract.View, SettingPresenter>(),
    SettingContract.View {

    private lateinit var logoutBtn: Button
    private lateinit var toolbar: Toolbar

    override fun getLayoutResId(): Int {
        return R.layout.activity_setting
    }

    override fun createPresenter(): SettingPresenter {
        return SettingPresenter()
    }

    override fun initView() {
        toolbar = findViewById(R.id.tb_setting)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "设置"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = dp2px(mContext, 5f)
        toolbar.setNavigationOnClickListener { finish() }
        logoutBtn = findViewById(R.id.btn_logout)
        logoutBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                presenter.logout()
            }
        })
    }

    override fun initData() {
        super.initData()
        val loggedIn = isCookieNotEmpty(mContext)
        if (loggedIn) {
            logoutBtn.visibility = View.VISIBLE
        } else {
            logoutBtn.visibility = View.GONE
        }
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun onLogoutResult() {
        MainApp.getInstance().getPersistentCookieJar().clear()
        EventBus.getDefault().post(LoggedInEvent(null))
        finish()
    }
}
