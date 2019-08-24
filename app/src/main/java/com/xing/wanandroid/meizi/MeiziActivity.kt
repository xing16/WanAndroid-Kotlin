package com.xing.wanandroid.meizi

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xing.wanandroid.R
import com.xing.wanandroid.base.mvp.BaseMVPActivity
import com.xing.wanandroid.image.ImageBrowseActivity
import com.xing.wanandroid.meizi.adapter.MeiziAdapter
import com.xing.wanandroid.meizi.bean.Meizi
import com.xing.wanandroid.meizi.contract.MeiziContract
import com.xing.wanandroid.meizi.presenter.MeiziPresenter
import com.xing.wanandroid.utils.IMAGES
import com.xing.wanandroid.utils.INDEX
import com.xing.wanandroid.utils.dp2px
import com.xing.wanandroid.utils.gotoActivity

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
        setSupportActionBar(toolbar)
        supportActionBar?.title = "妹子"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = dp2px(mContext, 5f).toFloat()
        toolbar.setNavigationOnClickListener { finish() }
        recyclerView = findViewById(R.id.rv_meizi)
        recyclerView.layoutManager = GridLayoutManager(mContext, 2)
        meiziAdapter = MeiziAdapter(R.layout.item_meizi)
        recyclerView.adapter = meiziAdapter
    }

    override fun initData() {
        super.initData()
        presenter.getMeiziList()
        setListener()
    }

    private fun setListener() {
        meiziAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val imgUrlList = convert2UrlList(dataList)
                val bundle = Bundle()
                bundle.putInt(INDEX, position)
                bundle.putStringArrayList(IMAGES, imgUrlList)
                gotoActivity(
                    mContext as Activity,
                    ImageBrowseActivity().javaClass,
                    bundle
                )
            }
        }

    }

    private fun convert2UrlList(dataList: ArrayList<Meizi>): ArrayList<String> {
        val urlList = ArrayList<String>()
        for (meizi in dataList) {
            urlList.add(meizi.url)
        }
        return urlList
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
