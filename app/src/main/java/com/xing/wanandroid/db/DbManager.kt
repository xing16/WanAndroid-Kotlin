package com.xing.wanandroid.db

class DbManager private constructor() {

    companion object {
        val instance = DbManagerHolder.holder
    }

    private object DbManagerHolder {
        val holder = DbManager()
    }
}