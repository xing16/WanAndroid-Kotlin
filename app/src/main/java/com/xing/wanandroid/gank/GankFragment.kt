package com.xing.wanandroid.gank


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.xing.wanandroid.R
import com.xing.wanandroid.base.mvp.BaseMVPFragment
import com.xing.wanandroid.gank.adapter.GankTodayAdapter
import com.xing.wanandroid.gank.adapter.WxPublicAdapter
import com.xing.wanandroid.gank.bean.GankToday
import com.xing.wanandroid.gank.bean.GankTodayEntity
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
    private lateinit var gankTodayAdapter: GankTodayAdapter

    override fun getLayoutResId(): Int {
        return R.layout.fragment_gank
    }

    override fun createPresenter(): GankPresenter {
        return GankPresenter()
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        recyclerView = rootView?.findViewById(R.id.rv_gank_today)
        recyclerView?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

    }

    override fun initData() {
        super.initData()
//        presenter.getWxPublic()
        presenter.getGankToday()
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun onWxPublic(list: List<WxPublic>) {
//        adapter.setNewData(list)
    }

    override fun onGankToday(map: HashMap<String, List<GankToday>>) {
        Log.e("debug", "map = $map")
        val list: ArrayList<GankTodayEntity<GankToday>> = ArrayList()
        for (key in map.keys) {
            val size = map.get(key)?.size ?: 0
            for (i in 0 until size) {
                val gankToday = map.get(key)?.get(i)
                if (gankToday != null) {
                    list.add(GankTodayEntity(gankToday))
                }
            }
        }
        gankTodayAdapter = GankTodayAdapter(R.layout.item_gank_section_item, R.layout.item_gank_section_header, list)
        recyclerView?.adapter = gankTodayAdapter
    }


    companion object {
        @JvmStatic
        fun newInstance() = GankFragment()
    }


}
