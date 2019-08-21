package com.xing.wanandroid.meizi

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.xing.wanandroid.R
import com.xing.wanandroid.base.mvp.BaseMVPActivity
import com.xing.wanandroid.meizi.adapter.MeiziAdapter
import com.xing.wanandroid.meizi.bean.Meizi
import com.xing.wanandroid.meizi.contract.MeiziContract
import com.xing.wanandroid.meizi.presenter.MeiziPresenter

class MeiziActivity : BaseMVPActivity<MeiziContract.View, MeiziPresenter>(), MeiziContract.View {

    private lateinit var recyclerView: RecyclerView
    private lateinit var meiziAdapter: MeiziAdapter
    private var dataList: ArrayList<Meizi> = ArrayList()
    private lateinit var toolbar: Toolbar

    override fun getLayoutResId(): Int {
        return R.layout.activity_meizi
    }

    override fun initView() {
        toolbar = findViewById(R.id.tb_meizi)
        toolbar.title = "妹子"
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { finish() }
        recyclerView = findViewById(R.id.rv_meizi)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        meiziAdapter = MeiziAdapter(R.layout.item_meizi)
        recyclerView.adapter = meiziAdapter
    }

    override fun initData() {
        super.initData()
        presenter.getMeiziList()
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun onMeiziList(list: List<Meizi>) {
        dataList.addAll(list)
        meiziAdapter.setNewData(dataList)
    }

    override fun createPresenter(): MeiziPresenter {
        return MeiziPresenter()
    }

}
