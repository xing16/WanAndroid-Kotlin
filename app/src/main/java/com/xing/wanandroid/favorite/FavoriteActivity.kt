package com.xing.wanandroid.favorite

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.xing.wanandroid.R
import com.xing.wanandroid.base.mvp.BaseMVPActivity
import com.xing.wanandroid.favorite.adapter.FavoriteAdapter
import com.xing.wanandroid.favorite.contract.FavoriteContract
import com.xing.wanandroid.favorite.presenter.FavoritePresenter
import com.xing.wanandroid.home.bean.ArticleResponse

class FavoriteActivity : BaseMVPActivity<FavoriteContract.View, FavoritePresenter>(),
    FavoriteContract.View {

    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter
    private var curPage = 0

    override fun getLayoutResId(): Int {
        return R.layout.activity_favorite
    }

    override fun createPresenter(): FavoritePresenter {
        return FavoritePresenter()
    }

    override fun initView() {
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
