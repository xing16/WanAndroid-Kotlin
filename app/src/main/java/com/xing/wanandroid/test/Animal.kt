package com.xing.wanandroid.test

open class Animal(var name: String) : IAnimal {
    var lastname : String = name

    // override 修饰的成员本身就是 open 的，
    override fun run() {
        println("Animal run")
    }

    fun eat() {
        println("eat")
    }
}