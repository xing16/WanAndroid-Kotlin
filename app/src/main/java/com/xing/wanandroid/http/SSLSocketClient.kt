package com.xing.wanandroid.http

import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

class SSLSocketClient {

    companion object {
        fun getSSLSocketFactory(): SSLSocketFactory {
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, getTrustManager(), SecureRandom())
            return sslContext.socketFactory
        }

        fun getTrustManager(): Array<TrustManager> {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

            })
            return trustAllCerts
        }

        fun getHostnameVerifier(): HostnameVerifier {
            val hostnameVerifier: HostnameVerifier = object : HostnameVerifier {
                override fun verify(hostname: String?, session: SSLSession?): Boolean {
                    return true
                }

            }
            return hostnameVerifier
        }
    }
}