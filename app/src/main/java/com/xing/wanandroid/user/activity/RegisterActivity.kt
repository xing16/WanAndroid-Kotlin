package com.xing.wanandroid.user.activity

import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.xing.wanandroid.R
import com.xing.wanandroid.base.mvp.BaseMVPActivity
import com.xing.wanandroid.user.bean.RegisterResponse
import com.xing.wanandroid.user.contract.RegisterContract
import com.xing.wanandroid.user.presenter.RegisterPresenter
import com.xing.wanandroid.widget.ClearEditText

class RegisterActivity : BaseMVPActivity<RegisterContract.View, RegisterPresenter>(),
    RegisterContract.View, View.OnClickListener {

    private lateinit var toolbar: Toolbar
    private lateinit var usernameEditText: ClearEditText
    private lateinit var passwordEditText: ClearEditText
    private lateinit var repasswordEditText: ClearEditText
    private lateinit var registerBtn: Button

    override fun getLayoutResId(): Int {
        return R.layout.activity_register
    }

    override fun createPresenter(): RegisterPresenter {
        return RegisterPresenter()
    }

    override fun initView() {
        toolbar = findViewById(R.id.tb_register)
        setSupportActionBar(toolbar)
        supportActionBar?.elevation = 10f
        supportActionBar?.setTitle(R.string.register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
        usernameEditText = findViewById(R.id.cet_register_username)
        passwordEditText = findViewById(R.id.cet_register_password)
        repasswordEditText = findViewById(R.id.cet_register_repassword)
        registerBtn = findViewById(R.id.btn_register)
    }

    override fun initData() {
        super.initData()
        registerBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                register()
            }
        }
    }

    fun register() {
        val username = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val repassword = repasswordEditText.text.toString().trim()
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(mContext, "请输入用户名", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "请输入密码", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(repassword)) {
            Toast.makeText(mContext, "请再次输入密码", Toast.LENGTH_LONG).show()
            return
        }
        if (!TextUtils.equals(password, repassword)) {
            Toast.makeText(mContext, "两次密码不一致", Toast.LENGTH_LONG).show()
            return
        }
        presenter.register(username, password, repassword)
    }


    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun onRegisterResult(result: RegisterResponse?) {
        Toast.makeText(mContext, "注册成功", Toast.LENGTH_LONG).show()
        finish()
    }
}
