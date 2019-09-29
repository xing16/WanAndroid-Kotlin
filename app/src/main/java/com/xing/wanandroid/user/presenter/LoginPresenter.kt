package com.xing.wanandroid.user.presenter

import com.xing.wanandroid.apiservice.ApiService
import com.xing.wanandroid.base.mvp.BasePresenter
import com.xing.wanandroid.db.DbManager
import com.xing.wanandroid.db.bean.User
import com.xing.wanandroid.http.BaseObserver
import com.xing.wanandroid.main.bean.LoggedInEvent
import com.xing.wanandroid.user.contract.LoginContract
import org.greenrobot.eventbus.EventBus

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun login(username: String, password: String) {
        addSubscribe(create(ApiService::class.java).login(username, password), object : BaseObserver<User>(getView()) {
            override fun onSuccess(user: User?) {
                getView()?.onLoginResult(username, user)
                DbManager.getInstance().getUserDao().deleteAll()
                DbManager.getInstance().getUserDao().insert(user)
                EventBus.getDefault().post(LoggedInEvent(user))
            }
        })
    }
}