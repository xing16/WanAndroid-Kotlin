package com.xing.wanandroid.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import butterknife.OnClick
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xing.wanandroid.R
import com.xing.wanandroid.utils.blur
import com.xing.wanandroid.web.adapter.WebOptAdapter
import com.xing.wanandroid.web.bean.WebOptBean

class WebDialogFragment : DialogFragment() {

    private lateinit var mContext: Context
    private var dataList: ArrayList<WebOptBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("debug", "oncreate")
        setStyle(STYLE_NO_TITLE, R.style.WebDialogFragmentStyle) //dialog全屏

        initData()

    }

    override fun onStart() {
        super.onStart()
        Log.e("debug", "onstart")
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable())
        dialog.window?.setDimAmount(0f)
        val attribute = dialog.window?.attributes
        attribute?.height = WindowManager.LayoutParams.WRAP_CONTENT   // 高度为 wrap_content，布局文件中设置无效
        attribute?.gravity = Gravity.TOP
        attribute?.windowAnimations = R.style.WebDialogFragmentAnimation
        dialog.window?.attributes = attribute
    }

    private fun initData() {
        dataList.clear()
        dataList.add(WebOptBean(R.drawable.ic_favorite_white, "收藏"))
        dataList.add(WebOptBean(R.drawable.ic_share, "微信分享"))
        dataList.add(WebOptBean(R.drawable.ic_link, "复制链接"))
        dataList.add(WebOptBean(R.drawable.ic_browser, "浏览器打开"))
        dataList.add(WebOptBean(R.drawable.ic_browser, "浏览器打开"))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("debug", "oncreateview")
        val rootView: View = inflater.inflate(R.layout.dialog_web, container, false)
        val parentView: View = rootView.findViewById(R.id.ll_web_dialog_parent)
        val closeImgView: View = rootView.findViewById(R.id.iv_dialog_web_close)
        closeImgView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                dismissAllowingStateLoss()
            }
        })
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.android_logo1)
        val blurBitmap = blur(mContext, bitmap, 10)
        parentView.background = BitmapDrawable(blurBitmap)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.rv_web_opt)
        recyclerView.layoutManager = GridLayoutManager(mContext, 4)
        val adapter = WebOptAdapter(R.layout.item_web_opt, dataList)
        adapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                when (position) {

                }
            }

        }
        recyclerView.adapter = adapter

        return rootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return super.onCreateDialog(savedInstanceState)
    }

}