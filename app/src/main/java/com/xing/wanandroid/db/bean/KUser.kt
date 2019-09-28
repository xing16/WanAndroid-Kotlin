package com.xing.wanandroid.db.bean

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id

@Entity
data class KUser(
    @Id
    val sid: Long,
    val admin: Boolean,
    val collectionIds: ArrayList<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)