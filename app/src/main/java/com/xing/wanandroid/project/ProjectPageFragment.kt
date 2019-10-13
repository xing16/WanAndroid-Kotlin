package com.xing.wanandroid.project

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.xing.wanandroid.R
import com.xing.wanandroid.base.BaseLazyFragment
import com.xing.wanandroid.image.ImageBrowseActivity
import com.xing.wanandroid.project.adapter.ProjectAdapter
import com.xing.wanandroid.project.bean.Project
import com.xing.wanandroid.project.bean.ProjectResponse
import com.xing.wanandroid.project.contract.ProjectPageContract
import com.xing.wanandroid.project.presenter.ProjectPagePresenter
import com.xing.wanandroid.utils.*
import com.xing.wanandroid.web.WebViewActivity
import com.xing.wanandroid.widget.LinearItemDecoration

private const val CID = "cid"

class ProjectPageFragment : BaseLazyFragment<ProjectPageContract.View, ProjectPagePresenter>(),
    ProjectPageContract.View {

    private var mCurPage: Int = 1
    private var cid: Int = 0
    private var listener: OnFragmentInteractionListener? = null
    private var recyclerView: RecyclerView? = null
    private var refreshLayout: SmartRefreshLayout? = null
    private lateinit var mAdapter: ProjectAdapter
    private var dataList = ArrayList<Project>()

    override fun getLayoutResId(): Int {
        return R.layout.fragment_project_page
    }

    override fun createPresenter(): ProjectPagePresenter {
        return ProjectPagePresenter()
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        recyclerView = rootView?.findViewById(R.id.rv_project)
        val itemDecoration = LinearItemDecoration(mContext).color(mContext.resources.getColor(R.color.white_eaeaea))
            .height(1f)
            .margin(15f, 15f)
        recyclerView?.addItemDecoration(itemDecoration)
        refreshLayout = rootView?.findViewById(R.id.srl_project)
        refreshLayout?.setEnableRefresh(false)
        recyclerView?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        setListener()
    }

    private fun setListener() {
        refreshLayout?.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                presenter.getProjectLists(mCurPage, cid)
            }
        })
    }

    override fun loadData() {
        presenter.getProjectLists(mCurPage, cid)
        mAdapter = ProjectAdapter(R.layout.item_project)
        recyclerView?.adapter = mAdapter
        mAdapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { adapter, view, position -> onItemClick(position) }
        mAdapter.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position -> onItemChildClick(position) }
    }

    override fun onProjectLists(page: Int, response: ProjectResponse?) {
        refreshLayout?.finishLoadMore()
        mCurPage = page + 1
        if (response?.datas != null) {
            dataList.addAll(response.datas)
        }
        mAdapter.setNewData(dataList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cid = it.getInt(CID)
        }
    }

    fun onItemClick(position: Int) {
        val bundle = Bundle()
        val bean = dataList.get(position)
        bundle.putString(URL, bean.link)
        bundle.putInt(ID, bean.id)
        bundle.putString(AUTHOR, bean.author)
        bundle.putString(LINK, bean.link)
        bundle.putString(TITLE, bean.title)
        gotoActivity(
            mContext as Activity,
            WebViewActivity().javaClass,
            bundle
        )
    }

    fun onItemChildClick(position: Int) {
        val imgUrl = dataList.get(position).envelopePic
        val list = ArrayList<String>()
        list.add(imgUrl)
        val bundle = Bundle()
        bundle.putStringArrayList(IMAGES, list)
        gotoActivity(
            mContext as Activity,
            ImageBrowseActivity().javaClass,
            bundle
        )
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
