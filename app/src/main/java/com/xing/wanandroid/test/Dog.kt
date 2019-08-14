package com.xing.wanandroid.test

class Dog(name: String) : Animal(name) {

    init {
        println("init() name = $name")
    }

    override fun run() {
        println("run name = $name")   // 编译不过
    }
}

class Cat(name: String) : Animal(name) {
    init {
        println("init() name = $name")
    }

    override fun run() {
        println("run() name = $name")
    }


     fun fly() {

    }

}