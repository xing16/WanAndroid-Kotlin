package com.xing.wanandroid.http

data class ApiException(var errCode: Int, var errMsg: String) : Exception()