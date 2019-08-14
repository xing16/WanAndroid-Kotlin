package com.xing.wanandroid.base

data class BaseResponse<T>(var errCode: Int, var errMsg: String, var data: T)
