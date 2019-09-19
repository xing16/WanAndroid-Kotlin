package com.xing.wanandroid.favorite

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.xing.wanandroid.R
import com.xing.wanandroid.base.mvp.BaseMVPActivity
import com.xing.wanandroid.favorite.adapter.FavoriteAdapter
import com.xing.wanandroid.favorite.contract.FavoriteContract
import com.xing.wanandroid.favorite.presenter.FavoritePresenter
import com.xing.wanandroid.home.bean.ArticleResponse
import com.xing.wanandroid.utils.dp2px

class FavoriteActivity : BaseMVPActivity<FavoriteContract.View, FavoritePresenter>(),
    FavoriteContract.View {

    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter
    private var curPage = 0
    private lateinit var toolbar: Toolbar

    override fun getLayoutResId(): Int {
        return R.layout.activity_favorite
    }

    override fun createPresenter(): FavoritePresenter {
        return FavoritePresenter()
    }

    override fun initView() {
        toolbar = findViewById(R.id.tb_favorite)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "收藏"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = dp2px(mContext, 5f).toFloat()
        toolbar.setNavigationOnClickListener { finish() }
        recyclerView = findViewById(R.id.rv_favorite)
        recyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        favoriteAdapter = FavoriteAdapter(R.layout.item_home_recycler)
        recyclerView.adapter = favoriteAdapter
    }

    override fun initData() {
        super.initData()
        presenter.getArticleFavorites(curPage)
    }

    override fun onArticleFavorite(response: ArticleResponse) {

    }


    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

}
