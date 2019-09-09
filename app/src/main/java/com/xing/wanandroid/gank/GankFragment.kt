package com.xing.wanandroid.gank


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xing.wanandroid.R
import com.xing.wanandroid.base.mvp.BaseMVPFragment
import com.xing.wanandroid.gank.adapter.WxPublicAdapter
import com.xing.wanandroid.gank.bean.WxPublic
import com.xing.wanandroid.gank.contract.GankContract
import com.xing.wanandroid.gank.presenter.GankPresenter


/**
 * A simple [Fragment] subclass.
 *
 */
class GankFragment : BaseMVPFragment<GankContract.View, GankPresenter>(), GankContract.View {

    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: WxPublicAdapter

    override fun getLayoutResId(): Int {
        return R.layout.fragment_gank
    }

    override fun createPresenter(): GankPresenter {
        return GankPresenter()
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        recyclerView = rootView?.findViewById(R.id.rv_wx_public)
        recyclerView?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        adapter = WxPublicAdapter(R.layout.item_wx_public)
        recyclerView?.adapter = adapter
    }

    override fun initData() {
        super.initData()
        presenter.getWxPublic()
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun onWxPublic(list: List<WxPublic>) {
        adapter.setNewData(list)
    }


    companion object {
        @JvmStatic
        fun newInstance() = GankFragment()
    }


}
