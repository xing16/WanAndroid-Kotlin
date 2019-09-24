package com.xing.wanandroid.common.cookie

import io.reactivex.internal.util.VolatileSizeArrayList
import okhttp3.Cookie
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class SerializableCookies(var cookie: Cookie) : Serializable {

    private var clientCookie: Cookie? = null

    fun getCookies(): Cookie? {
        var bestCookie: Cookie? = cookie
        if (clientCookie != null) {
            bestCookie = clientCookie
        }
        return bestCookie
    }

    fun writeObject(out: ObjectOutputStream) {
        out.writeObject(cookie.name())
        out.writeObject(cookie.value())
        out.writeLong(cookie.expiresAt())
        out.writeObject(cookie.domain())
        out.writeObject(cookie.path())
        out.writeBoolean(cookie.secure())
        out.writeBoolean(cookie.httpOnly())
        out.writeBoolean(cookie.hostOnly())
        out.writeBoolean(cookie.persistent())
    }

    fun readObject(input: ObjectInputStream) {
        val name: String = input.readObject() as String
        val value: String = input.readObject() as String
        val expiresAt = input.readLong()
        val domain: String = input.readObject() as String
        val path: String = input.readObject() as String
        val secure: Boolean = input.readBoolean()
        val httpOnly = input.readBoolean()
        val hostOnly = input.readBoolean()
        val persistent = input.readBoolean()
        val builder = Cookie.Builder()
        builder.name(name)
            .value(value)
            .expiresAt(expiresAt)
            .path(path)
        if (hostOnly) builder.hostOnlyDomain(domain) else builder.domain(domain)
        if (secure) builder.secure()
        if (httpOnly) builder.httpOnly()
        clientCookie = builder.build()
    }


}