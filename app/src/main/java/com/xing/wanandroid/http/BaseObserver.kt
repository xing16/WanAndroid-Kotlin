package com.xing.wanandroid.http

import android.util.Log
import com.xing.wanandroid.base.BaseResponse
import com.xing.wanandroid.base.mvp.IView
import io.reactivex.observers.DisposableObserver

abstract class BaseObserver<T> : DisposableObserver<BaseResponse<T>> {

    private var baseView: IView? = null

    constructor() : super()

    constructor(view: IView?) : super() {
        baseView = view
    }

    override fun onStart() {
        super.onStart()
        baseView?.showLoading()
    }

    override fun onNext(response: BaseResponse<T>) {
        baseView?.dismissLoading()
        Log.e("debug", "response = ${response.errorCode}")
        val errorCode: Int = response.errorCode ?: -1
        val errorMsg: String = response.errorMsg ?: ""
        val error: Boolean = response.error ?: true
        Log.e("debug", "errCode = $errorCode")
        Log.e("debug", "error = $error")
        if ((errorCode == 0) or (errorCode == 200)) {
            if (response.data != null) {
                onSuccess(response.data)
            } else {

            }

        } else if (!error) {
            if (response.results != null) {
                onSuccess(response.results)
            } else {
                onSuccess(Any() as T)
            }
        } else {
            onError(ApiException(errorCode, errorMsg))
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