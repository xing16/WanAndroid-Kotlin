package com.xing.wanandroid.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory

class WeChatShareUtils {

    companion object {
        fun shareWeb(context: Context, appId: String, url: String, title: String, desc: String?, bitmap: Bitmap?, scene: Int) {
            val wxapi = WXAPIFactory.createWXAPI(context, appId)
            // 检查微信是否安装
            if (!wxapi.isWXAppInstalled()) {
                Toast.makeText(context, "微信未安装", Toast.LENGTH_LONG).show()
                return
            }
            val webPageObj = WXWebpageObject()
            webPageObj.webpageUrl = url
            val wxMediaMessage = WXMediaMessage(webPageObj)
            // 网页标题
            wxMediaMessage.title = title
            // 网页描述
            wxMediaMessage.description = desc
            // 缩略图
            wxMediaMessage.setThumbImage(bitmap)
            // 构造 req
            val req = SendMessageToWX.Req()
            // transaction表示唯一一个请求
            req.transaction = "webpage"
            req.message = wxMediaMessage
            req.scene = scene
            wxapi.sendReq(req)
        }
    }

}