package com.xing.wanandroid.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xing.wanandroid.R

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_search_container, SearchHistoryFragment.newInstance())
        transaction.commitAllowingStateLoss()

    }
}
