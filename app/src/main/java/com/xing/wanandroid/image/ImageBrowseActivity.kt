package com.xing.wanandroid.image

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaeger.library.StatusBarUtil
import com.xing.wanandroid.R
import com.xing.wanandroid.base.BaseActivity
import com.xing.wanandroid.image.adapter.ImageBrowseAdapter
import com.xing.wanandroid.utils.IMAGES
import com.xing.wanandroid.utils.INDEX

class ImageBrowseActivity : BaseActivity() {

    private lateinit var backImgView: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageBrowseAdapter: ImageBrowseAdapter
    private var list = ArrayList<String>()
    private var index: Int = 0

    override fun getLayoutResId(): Int {
        return R.layout.activity_image_browse
    }

    override fun initView() {
        StatusBarUtil.setDarkMode(this)  // 状态栏字体颜色
        StatusBarUtil.setColor(this, resources.getColor(android.R.color.black), 0)
        backImgView = findViewById(R.id.iv_browse_back)
        recyclerView = findViewById(R.id.rv_images_browse)
    }

    override fun initData() {
        super.initData()
       val linearLayoutManager =  LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        val bundle = intent.extras
        index = bundle?.getInt(INDEX) ?: 0
        list = bundle?.getStringArrayList(IMAGES) ?: ArrayList()
        imageBrowseAdapter = ImageBrowseAdapter(R.layout.item_image_browse)
        imageBrowseAdapter.setNewData(list)
        recyclerView.adapter = imageBrowseAdapter

        backImgView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })


    }
}
