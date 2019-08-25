package com.xing.wanandroid.user.activity

import android.text.SpannableString
import android.text.TextUtils
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.xing.wanandroid.R
import com.xing.wanandroid.base.mvp.BaseMVPActivity
import com.xing.wanandroid.user.bean.LoginResponse
import com.xing.wanandroid.user.contract.LoginContract
import com.xing.wanandroid.user.presenter.LoginPresenter
import com.xing.wanandroid.utils.gotoActivity
import com.xing.wanandroid.widget.ClearEditText

class LoginActivity : BaseMVPActivity<LoginContract.View, LoginPresenter>(),
    LoginContract.View, View.OnClickListener {

    private lateinit var usernameEditText: ClearEditText
    private lateinit var passwordEditText: ClearEditText
    private lateinit var loginBtn: Button
    private lateinit var registerTxtView: TextView


    override fun getLayoutResId(): Int {
        return R.layout.activity_login
    }

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter()
    }

    override fun initView() {
        usernameEditText = findViewById(R.id.cet_login_username)
        passwordEditText = findViewById(R.id.cet_login_password)
        loginBtn = findViewById(R.id.bnt_login)
        registerTxtView = findViewById(R.id.tv_user_register)
    }

    override fun initData() {
        super.initData()
        val spannableString = SpannableString(registerTxtView.text.toString().trim())
        spannableString.setSpan(
            UnderlineSpan(),
            0,
            registerTxtView.text.toString().trim().length,
            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
        )
        registerTxtView.text = spannableString
        registerTxtView.setOnClickListener(this)
        loginBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bnt_login -> {
                login()
            }
            R.id.tv_user_register -> {
                gotoRegisterActivity()
            }
        }
    }

    private fun login() {
        val username = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(mContext, "请输入用户名", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "请输入密码", Toast.LENGTH_LONG).show()
            return
        }
        presenter.login(username, password)
    }


    private fun gotoRegisterActivity() {
        gotoActivity(this, RegisterActivity().javaClass)
    }


    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun onLoginResult(result: LoginResponse) {
        finish()
    }


}
