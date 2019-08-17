package com.xing.wanandroid.widget.flowlayout

import android.view.View
import android.view.ViewGroup

abstract class FlowAdapter<T> {

    private var dataList: List<T> = ArrayList()

    constructor(list: ArrayList<T>) {
        this.dataList = list
    }

    fun getItem(position: Int): T {
        return dataList[position]
    }

    fun getCount(): Int {
        return dataList.size
    }

    abstract fun getView(position: Int, t: T, parent: ViewGroup): View

}