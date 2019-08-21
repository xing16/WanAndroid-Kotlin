package com.xing.wanandroid.search

import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.xing.wanandroid.R
import com.xing.wanandroid.base.BaseActivity
import com.xing.wanandroid.utils.hideKeyboard

class SearchActivity : BaseActivity(), SearchHistoryFragment.OnSearchTextListener {

    private lateinit var editText: EditText
    private lateinit var backImgView: ImageView

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
        var searchTxtView: TextView = findViewById(R.id.tv_search)
        searchTxtView.setOnClickListener {
            hideKeyboard(editText)
            val keyword = editText.text.toString().trim()
            if (TextUtils.isEmpty(keyword)) {
                Toast.makeText(SearchActivity@ this, "请输入搜索内容", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_search_container, SearchResultFragment.newInstance(keyword))
            transaction.commitAllowingStateLoss()

        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_search_container, SearchHistoryFragment.newInstance())
        transaction.commitAllowingStateLoss()
    }


    override fun onSearchText(text: String) {
        editText.setText(text)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_search_container, SearchResultFragment.newInstance(text))
        transaction.commitAllowingStateLoss()
    }

}
