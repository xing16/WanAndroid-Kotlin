package com.xing.wanandroid.search

import android.animation.IntEvaluator
import android.animation.ObjectAnimator
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.xing.wanandroid.R
import com.xing.wanandroid.base.BaseActivity
import com.xing.wanandroid.utils.getScreenWidth
import com.xing.wanandroid.utils.hideKeyboard
import com.xing.wanandroid.widget.ClearEditText
import com.xing.wanandroid.widget.ViewWrapper

class SearchActivity : BaseActivity(), SearchHistoryFragment.OnSearchTextListener {

    private lateinit var editText: ClearEditText
    private lateinit var backImgView: ImageView
    private lateinit var searchTxtView: TextView

    override fun getLayoutResId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        backImgView = findViewById(R.id.iv_search_back)
        backImgView.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }
        editText = findViewById(R.id.et_search_input)
        initAnimation()

        searchTxtView = findViewById(R.id.tv_search)
        searchTxtView.setOnClickListener {
            hideKeyboard(editText)
            val keyword = editText.text.toString().trim()
            if (TextUtils.isEmpty(keyword)) {
                Toast.makeText(SearchActivity@ this, "请输入搜索内容", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            changeFragment(SearchResultFragment.newInstance(keyword))
        }
        changeFragment(SearchHistoryFragment.newInstance())
    }

    private fun initAnimation() {
        editText.post(object : Runnable {
            override fun run() {
                val viewWrapper = ViewWrapper(editText)
                val objectAnimator =
                    ObjectAnimator.ofObject(
                        viewWrapper,
                        "width",
                        IntEvaluator(),
                        editText.width,
                        getScreenWidth(mContext) - searchTxtView.width * 3
                    )
                objectAnimator.duration = 200
                objectAnimator.interpolator = LinearInterpolator()
                objectAnimator.start()
            }
        })

    }


    override fun onSearchText(text: String) {
        editText.setText(text)
        val fragment = SearchResultFragment.newInstance(text)
        changeFragment(fragment)
    }

    private fun changeFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_search_container, fragment)
        transaction.commitAllowingStateLoss()
    }
}
