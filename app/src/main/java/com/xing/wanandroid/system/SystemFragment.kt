package com.xing.wanandroid.system

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xing.wanandroid.R
import com.xing.wanandroid.project.adapter.ProjectPageAdapter
import com.xing.wanandroid.base.mvp.BaseMVPFragment
import com.xing.wanandroid.system.bean.SystemCategory
import com.xing.wanandroid.system.contract.SystemContract
import com.xing.wanandroid.system.presenter.SystemPresenter

private const val ARG_PARAM1 = "param1"

class SystemFragment : BaseMVPFragment<SystemContract.View, SystemPresenter>(),
    SystemContract.View {

    private var categoryRecyclerView: RecyclerView? = null
    private var contentRecyclerView: RecyclerView? = null
    private lateinit var systemCategoryAdapter: SystemCategoryAdapter
    private lateinit var contentAdapter: SystemCategoryAdapter
    private var dataList: List<SystemCategory> = ArrayList()

    private var param1: String? = null
    private lateinit var adapter: ProjectPageAdapter

    override fun getLayoutResId(): Int {
        return R.layout.fragment_system
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        categoryRecyclerView = rootView?.findViewById(R.id.rv_system_category)
        contentRecyclerView = rootView?.findViewById(R.id.rv_system_content)
    }

    override fun initData() {
        super.initData()
        categoryRecyclerView?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        contentRecyclerView?.layoutManager = GridLayoutManager(mContext, 2)
        systemCategoryAdapter = SystemCategoryAdapter(R.layout.item_system_category)
        contentAdapter = SystemCategoryAdapter(R.layout.item_system_category)

        systemCategoryAdapter.setOnItemClickListener(object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val systemCategory = dataList.get(position)
                contentAdapter.setNewData(systemCategory.children)
            }
        })
        categoryRecyclerView?.adapter = systemCategoryAdapter
        contentRecyclerView?.adapter = contentAdapter
        presenter.getSystemCategory()
    }


    override fun createPresenter(): SystemPresenter {
        return SystemPresenter()
    }


    override fun onSystemCategory(data: List<SystemCategory>) {
        dataList = data
        systemCategoryAdapter.setNewData(data)
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
        fun newInstance() = SystemFragment()
    }
}
