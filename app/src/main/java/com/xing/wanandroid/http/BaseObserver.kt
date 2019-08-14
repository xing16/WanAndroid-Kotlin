package com.xing.wanandroid.http

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
        var errcode: Int = response.errCode
        var errmsg: String = response.errMsg
        if ((errcode == 0) or (errcode == 200)) {
            var data: T = response.data
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