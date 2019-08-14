package com.xing.wanandroid.project

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xing.wanandroid.R
import com.xing.wanandroid.base.BaseLazyFragment
import com.xing.wanandroid.project.adapter.ProjectAdapter
import com.xing.wanandroid.project.bean.ProjectResponse
import com.xing.wanandroid.project.contract.ProjectPageContract
import com.xing.wanandroid.project.presenter.ProjectPagePresenter
import com.xing.wanandroid.utils.gotoActivity
import com.xing.wanandroid.web.WebViewActivity

private const val CID = "cid"

class ProjectPageFragment : BaseLazyFragment<ProjectPageContract.View, ProjectPagePresenter>(),
    ProjectPageContract.View {

    private var page: Int = 1
    private var cid: Int = 0
    private var listener: OnFragmentInteractionListener? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var mAdapter: ProjectAdapter

    override fun getLayoutResId(): Int {
        return R.layout.fragment_project_page
    }

    override fun createPresenter(): ProjectPagePresenter {
        return ProjectPagePresenter()
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        recyclerView = rootView?.findViewById(R.id.rv_project)
        recyclerView?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
    }

    override fun loadData() {
        presenter.getProjectLists(page, cid)
        mAdapter = ProjectAdapter(R.layout.item_project)
        recyclerView?.adapter = mAdapter
        mAdapter.onItemClickListener =
                BaseQuickAdapter.OnItemClickListener { adapter, view, position -> onItemClick(position) }
    }

    override fun onProjectLists(response: ProjectResponse) {
        mAdapter.setNewData(response.datas)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cid = it.getInt(CID)
        }
    }

    fun onItemClick(position: Int) {
    }


    override fun showLoading() {
    }

    override fun dismissLoading() {
    }


    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(cid: Int) =
            ProjectPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(CID, cid)
                }
            }
    }
}
