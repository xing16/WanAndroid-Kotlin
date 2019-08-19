package com.xing.wanandroid.http

data class ApiException(private var errCode: Int, private var errMsg: String?) : Exception()