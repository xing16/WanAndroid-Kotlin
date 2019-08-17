package com.xing.wanandroid.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.xing.wanandroid.R

class SearchActivity : AppCompatActivity(), SearchHistoryFragment.OnSearchTextListener {

    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        editText = findViewById(R.id.et_search_input)
        var searchTxtView: TextView = findViewById(R.id.tv_search)
        searchTxtView.setOnClickListener {
            val keyword = editText.text.toString().trim()
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
