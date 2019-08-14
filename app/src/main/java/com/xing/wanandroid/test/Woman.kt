package com.xing.wanandroid.test

class Woman(var name: String) : Person(name) {
    override fun pee() {
        println("女人蹲着的尿尿")
    }

    override fun eat() {
        println("女人 ${name} 小口的吃饭")
    }

}