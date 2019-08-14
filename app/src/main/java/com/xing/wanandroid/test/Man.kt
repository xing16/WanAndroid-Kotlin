package com.xing.wanandroid.test

class Man(var name: String) : Person(name) {
    override fun pee() {
        println("男人站着的尿尿")
    }

    override fun eat() {
        println("男人 ${name} 大口的吃饭")
    }
}