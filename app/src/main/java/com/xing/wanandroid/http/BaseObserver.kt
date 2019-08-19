package com.xing.wanandroid.http

import android.util.Log
import com.xing.wanandroid.base.BaseResponse
import com.xing.wanandroid.base.mvp.IView
import io.reactivex.observers.DisposableObserver

abstract class BaseObserver<T> : DisposableObserver<BaseResponse<T>> {

    private var baseView: IView? = null

    constructor() : super()

    constructor(view: IView) : super() {
        baseView = view
    }

    override fun onStart() {
        super.onStart()
        baseView?.showLoading()
    }

    override fun onNext(response: BaseResponse<T>) {
        baseView?.dismissLoading()
        Log.e("debug", "response = ${response.errCode}")
        val errcode: Int = response.errCode
        val errmsg: String? = response.errMsg
        val error: Boolean = response.error
        Log.e("debug", "errCode = ${errcode}")
        Log.e("debug", "error = ${error}")
        if ((errcode == 0) or (errcode == 200)) {
            val data: T = response.data
            onSuccess(data)
        } else if (!error) {
            val data: T = response.results
            Log.e("debug", "t = ${response.results}")
            onSuccess(data)
        } else {
            onError(ApiException(errcode, errmsg))
        }
    }

    override fun onError(e: Throwable) {
        ExceptionHandler.handleException(e)

    }

    abstract fun onSuccess(data: T)


    override fun onComplete() {
        baseView?.dismissLoading()
    }

}