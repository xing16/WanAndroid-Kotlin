package com.xing.wanandroid.gank.bean

import com.chad.library.adapter.base.entity.SectionEntity

class GankTodayEntity<T> : SectionEntity<T> {

    constructor(isHeader: Boolean, header: String) : super(isHeader, header)

    constructor(t: T) : super(t)


}