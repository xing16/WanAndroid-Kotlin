package com.xing.wanandroid.main.presenter

import android.util.Log
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.db.DbManager
import com.xing.wanandroid.main.contract.MainContract

class MainPresenter : BasePresenter<MainContract.View>(), MainContract.Presenter {

    override fun getUserInfo() {
        val users = DbManager.getInstance().getUserDao().loadAll()
        Log.e("MainPresenter", "users = $users")
        if (users.size > 0) {
            getView()?.onUserInfo(users[0])
        }
    }

}