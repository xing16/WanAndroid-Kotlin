package com.xing.wanandroid.home

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.xing.wanandroid.R
import com.xing.wanandroid.base.mvp.BaseMVPFragment
import com.xing.wanandroid.home.adapter.HomeRecyclerAdapter
import com.xing.wanandroid.home.bean.HomeRecommend
import com.xing.wanandroid.home.bean.HomeResponse
import com.xing.wanandroid.project.contract.HomeContract
import com.xing.wanandroid.project.presenter.HomePresenter
import com.youth.banner.Banner
import com.youth.banner.loader.ImageLoader
import kotlin.math.round

private const val ARG_PARAM1 = "param1"

class HomeFragment : BaseMVPFragment<HomeContract.View, HomePresenter>(), HomeContract.View {

    private var param1: String? = null
    private lateinit var adapter: HomeRecyclerAdapter
    private lateinit var banner: Banner
    private var recyclerView: RecyclerView? = null
    private lateinit var headerView: View
    private var page: Int = 0

    override fun getLayoutResId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        recyclerView = rootView?.findViewById(R.id.rv_home)
        headerView = layoutInflater.inflate(R.layout.layout_home_header, null, false)
        banner = headerView.findViewById(R.id.banner)
    }

    override fun createPresenter(): HomePresenter {
        return HomePresenter()
    }

    override fun initData() {
        super.initData()
        recyclerView?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        adapter = HomeRecyclerAdapter(R.layout.item_home_recycler)
        recyclerView?.adapter = adapter
        adapter.addHeaderView(headerView)

        presenter.getBanner()

        presenter.getRecommend(page)


    }

    override fun onBanner(list: List<com.xing.wanandroid.home.bean.Banner>) {
        var urlList = mutableListOf<String>()
        for (banner in list) {
            urlList.add(banner.imagePath)
        }
        banner.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                val roundedCorners = RoundedCorners(20)
                val bitmapTransform = RequestOptions.bitmapTransform(roundedCorners)
                Glide.with(context!!).load(path).apply(bitmapTransform).into(imageView!!)
            }
        })
        banner.setImages(urlList)
            .isAutoPlay(true)
            .start()

    }

    override fun onRecommend(response: HomeResponse) {
        Log.e("debug", "size = ${response.datas.size}")
        adapter.setNewData(response.datas)
    }


    override fun showLoading() {
    }

    override fun dismissLoading() {
    }


    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()

    }
}
