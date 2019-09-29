package com.xing.wanandroid.gank


import android.app.Activity
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xing.wanandroid.base.mvp.BaseMVPFragment
import com.xing.wanandroid.gank.adapter.GankTodayAdapter
import com.xing.wanandroid.gank.adapter.WxPublicAdapter
import com.xing.wanandroid.gank.bean.GankToday
import com.xing.wanandroid.gank.bean.GankTodayEntity
import com.xing.wanandroid.gank.bean.WxPublic
import com.xing.wanandroid.gank.contract.GankContract
import com.xing.wanandroid.gank.presenter.GankPresenter
import com.xing.wanandroid.utils.gotoActivity
import com.xing.wanandroid.web.WebViewActivity
import com.xing.wanandroid.widget.LinearItemDecoration


/**
 * gank fragment
 *
 */
class GankFragment : BaseMVPFragment<GankContract.View, GankPresenter>(), GankContract.View {

    private lateinit var headerView: View
    private lateinit var wxPublicRecyclerView: RecyclerView
    private lateinit var headerImgView: ImageView
    private var gankRecyclerView: RecyclerView? = null
    private lateinit var wxPublicAdapter: WxPublicAdapter
    private lateinit var gankTodayAdapter: GankTodayAdapter

    override fun getLayoutResId(): Int {
        return com.xing.wanandroid.R.layout.fragment_gank
    }

    override fun createPresenter(): GankPresenter {
        return GankPresenter()
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        headerView = LayoutInflater.from(mContext).inflate(com.xing.wanandroid.R.layout.layout_gank_header, null, false)
        wxPublicRecyclerView = headerView.findViewById(com.xing.wanandroid.R.id.rv_gank_header_wxpublic)
        headerImgView = headerView.findViewById(com.xing.wanandroid.R.id.iv_gank_header_img)
        gankRecyclerView = rootView?.findViewById(com.xing.wanandroid.R.id.rv_gank_today)

        // 初始化 wxPublicRecyclerView
        wxPublicRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        wxPublicAdapter = WxPublicAdapter(com.xing.wanandroid.R.layout.item_wx_public)
        wxPublicAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

            }
        }
        wxPublicRecyclerView.adapter = wxPublicAdapter

        // 初始化 gankRecyclerView
        gankRecyclerView?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = LinearItemDecoration(mContext).color(mContext.resources.getColor(com.xing.wanandroid.R.color.white_eaeaea))
            .height(1f)
            .margin(15f, 15f)
            .jumpPositions(arrayOf(0))
        gankRecyclerView?.addItemDecoration(itemDecoration)
    }

    override fun initData() {
        super.initData()

        presenter.getWxPublic()
        presenter.getGankToday()
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun onWxPublic(list: List<WxPublic>?) {
        wxPublicAdapter.setNewData(list)
    }

    override fun onGankToday(map: HashMap<String, List<GankToday>>?) {
        Log.e("debug", "map = $map")
        val list: ArrayList<GankTodayEntity<GankToday>> = ArrayList()
        if (map != null) {
            for (key in map.keys) {
                if ("福利" == key) {
                    val meiziImage = map.get(key)?.get(0)?.url
                    Glide.with(mContext).load(meiziImage).into(headerImgView)
//                val matrix = ColorMatrix()

//                matrix.setSaturation(0f)
//                val filter = ColorMatrixColorFilter(matrix)
//                headerImgView.setColorFilter(filter)
                    continue
                }
                list.add(GankTodayEntity(true, key))
                val size = map.get(key)?.size ?: 0
                for (i in 0 until size) {
                    val gankToday = map.get(key)?.get(i)
                    if (gankToday != null) {
                        list.add(GankTodayEntity(gankToday))
                    }
                }
            }
        }
        gankTodayAdapter = GankTodayAdapter(com.xing.wanandroid.R.layout.item_gank_section_item, com.xing.wanandroid.R.layout.item_gank_section_header, list)
        gankTodayAdapter.addHeaderView(headerView)
        gankTodayAdapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val url = list[position].t.url
                val bundle = Bundle()
                bundle.putString(WebViewActivity.URL, url)
                gotoActivity(mContext as Activity, WebViewActivity().javaClass, bundle)
            }
        }
        gankRecyclerView?.adapter = gankTodayAdapter
    }


    companion object {
        @JvmStatic
        fun newInstance() = GankFragment()
    }


}
