package com.xing.wanandroid.http

import android.widget.Toast
import com.google.gson.JsonParseException
import com.xing.wanandroid.app.MainApp
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException

class ExceptionHandler {


    companion object {
        private const val UNAUTHORIZED = 401
        private const val FORBIDDEN = 403
        private const val NOT_FOUND = 404
        private const val REQUEST_TIMEOUT = 408
        private const val INTERNAL_SERVER_ERROR = 500
        private const val BAD_GATEWAY = 502
        private const val SERVICE_UNAVAILABLE = 503
        private const val GATEWAY_TIMEOUT = 504
        fun handleException(exception: Throwable) {
            var errorMsg = ""
            if (exception is HttpException) {
                when (exception.code()) {
                    UNAUTHORIZED,
                    FORBIDDEN,
                    NOT_FOUND,
                    REQUEST_TIMEOUT,
                    GATEWAY_TIMEOUT,
                    INTERNAL_SERVER_ERROR,
                    BAD_GATEWAY,
                    SERVICE_UNAVAILABLE -> {
                        errorMsg = "网络错误"
                    }
                }
            } else if (exception is ApiException) {
                errorMsg = exception.errMsg
                val errorCode = exception.errCode
                // 根据 errorCode 处理服务端接口异常，如 token 登录失效
                handleServerException(errorCode)
            } else if ((exception is JsonParseException) or (exception is JSONException) or (exception is ParseException)) {
                errorMsg = "解析错误"
            } else if (exception is ConnectException) {
                errorMsg = "网络链接失败，请稍后重试"
            } else if (exception is SSLHandshakeException) {
                errorMsg = "证书验证失败"
            } else if (exception is ConnectTimeoutException) {
                errorMsg = "网络链接超时"
            } else if (exception is SocketTimeoutException) {
                errorMsg = "连接超时"
            } else {
                errorMsg = "网络链接异常，请稍后重试"
            }
            Toast.makeText(MainApp.getContext(), errorMsg, Toast.LENGTH_LONG).show()
        }

        private fun handleServerException(errorCode: Int) {
            when (errorCode) {

            }
        }
    }


}