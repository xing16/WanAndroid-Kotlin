package com.xing.wanandroid.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory


class WXEntryActivity : Activity(), IWXAPIEventHandler {

    private lateinit var wxapi: IWXAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.xing.wanandroid.R.layout.activity_wxentry)
        // 通过 WXAPIFactory 工厂，获取IWXAPI的实例
        wxapi = WXAPIFactory.createWXAPI(this, "")
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            wxapi.handleIntent(intent, this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onResp(resp: BaseResp?) {
        val result: Int
        when (resp?.errCode) {
            BaseResp.ErrCode.ERR_OK -> result = com.xing.wanandroid.R.string.share_success
            BaseResp.ErrCode.ERR_USER_CANCEL -> result = com.xing.wanandroid.R.string.share_cancel
            BaseResp.ErrCode.ERR_AUTH_DENIED -> result = com.xing.wanandroid.R.string.share_auth_failed
            BaseResp.ErrCode.ERR_UNSUPPORT -> result = com.xing.wanandroid.R.string.share_unsupported
            else -> result = com.xing.wanandroid.R.string.share_unknown
        }
        Toast.makeText(applicationContext, result, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onReq(p0: BaseReq?) {
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        wxapi.handleIntent(intent, this)
    }
}
