package com.xing.wanandroid.project

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.xing.wanandroid.R
import com.xing.wanandroid.project.adapter.ProjectPageAdapter
import com.xing.wanandroid.base.mvp.BaseMVPFragment
import com.xing.wanandroid.common.bean.FragmentItem
import com.xing.wanandroid.project.bean.ProjectTab
import com.xing.wanandroid.main.widgets.ProjectViewPager
import com.xing.wanandroid.project.contract.ProjectContract
import com.xing.wanandroid.project.presenter.ProjectPresenter

private const val ARG_PARAM1 = "param1"

class ProjectFragment : BaseMVPFragment<ProjectContract.View, ProjectPresenter>(), ProjectContract.View {

    private var param1: String? = null
    private lateinit var adapter: ProjectPageAdapter

    override fun getLayoutResId(): Int {
        return R.layout.fragment_project
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        val tabLayout: TabLayout? = rootView?.findViewById(R.id.tl_project_tabs)
        val viewPager: ProjectViewPager? = rootView?.findViewById(R.id.vp_project_pager)
        val fragmentList = mutableListOf<FragmentItem>()
        adapter = ProjectPageAdapter(childFragmentManager, fragmentList)
        viewPager?.adapter = adapter
        tabLayout?.setupWithViewPager(viewPager)
    }

    override fun createPresenter(): ProjectPresenter {
        return ProjectPresenter()
    }

    override fun initData() {
        super.initData()
        presenter.getProjectTabs()
    }

    override fun onProjectTabs(projectTabs: List<ProjectTab>?) {
        val projectTabsList = getFragmentItems(projectTabs)
        adapter.setDataSource(projectTabsList)
    }

    private fun getFragmentItems(projectTabs: List<ProjectTab>?): List<FragmentItem> {
        val list = mutableListOf<FragmentItem>()
        if (projectTabs != null) {
            for (projectTab in projectTabs) {
                list.add(
                    FragmentItem(
                        projectTab.name, ProjectPageFragment.newInstance(projectTab.id)
                    )
                )
            }
        }
        return list
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
        fun newInstance() = ProjectFragment()

    }
}
