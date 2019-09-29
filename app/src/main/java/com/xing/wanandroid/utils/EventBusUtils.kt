package com.xing.wanandroid.utils

import org.greenrobot.eventbus.EventBus


class EventBusUtils {
    companion object {
        fun register(obj: Any) {
            EventBus.getDefault().register(obj)
        }

        fun unregister(obj: Any) {
            EventBus.getDefault().unregister(obj)
        }

        fun post(obj: Any) {
            EventBus.getDefault().post(obj)
        }

    }
}