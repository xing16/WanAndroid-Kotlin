package com.xing.wanandroid.setting

import android.view.View
import android.widget.Button
import com.xing.wanandroid.R
import com.xing.wanandroid.app.MainApp
import com.xing.wanandroid.base.mvp.BaseMVPActivity
import com.xing.wanandroid.setting.contract.SettingContract
import com.xing.wanandroid.setting.presenter.SettingPresenter

class SettingActivity : BaseMVPActivity<SettingContract.View, SettingPresenter>(), SettingContract.View {

    private lateinit var logoutBtn: Button

    override fun getLayoutResId(): Int {
        return R.layout.activity_setting
    }

    override fun createPresenter(): SettingPresenter {
        return SettingPresenter()
    }

    override fun initView() {
        logoutBtn = findViewById(R.id.btn_logout)
        logoutBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                MainApp.getInstance().getPersistentCookieJar().clear()
            }
        })
    }


    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun onLogoutResult() {
    }
}
