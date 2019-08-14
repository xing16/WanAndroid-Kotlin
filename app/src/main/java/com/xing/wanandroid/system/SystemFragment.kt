package com.xing.wanandroid.system

import android.os.Bundle
import android.view.View
import com.xing.wanandroid.R
import com.xing.wanandroid.project.adapter.ProjectPageAdapter
import com.xing.wanandroid.base.mvp.BaseMVPFragment
import com.xing.wanandroid.system.contract.SystemContract
import com.xing.wanandroid.system.presenter.SystemPresenter

private const val ARG_PARAM1 = "param1"

class SystemFragment : BaseMVPFragment<SystemContract.View, SystemPresenter>(),
    SystemContract.View {

    private var param1: String? = null
    private lateinit var adapter: ProjectPageAdapter

    override fun getLayoutResId(): Int {
        return R.layout.fragment_system
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
    }

    override fun createPresenter(): SystemPresenter {
        return SystemPresenter()
    }

    override fun initData() {
        super.initData()

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
