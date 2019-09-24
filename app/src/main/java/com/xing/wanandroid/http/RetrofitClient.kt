package com.xing.wanandroid.http

import com.xing.wanandroid.utils.logInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.xing.wanandroid.app.MainApp


class RetrofitClient {

    var okHttpClient: OkHttpClient
    var retrofit: Retrofit

    private constructor() {

        okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
            .addInterceptor(logInterceptor())
            .cookieJar(MainApp.getInstance().getPersistentCookieJar())
//            .addInterceptor()
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        private var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    field = RetrofitClient()
                }
                return field
            }

        @Synchronized
        fun get(): RetrofitClient {
            return instance!!
        }
    }

}