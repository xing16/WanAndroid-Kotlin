package com.xing.wanandroid.test

/**
 * 密封类，所有的子类都必须在与该密封类自身文件相同的文件中声明
 */
sealed class Apple(val name: String) {

    open fun say() {
        println("Sealed base class")
    }
}

class AmericaApple(name: String) : Apple(name) {

    override fun say() {
        println("name = $name")
    }
}

fun main(list: Array<String>) {
    val apple = AmericaApple("AmericaApple")
    apple.say()
}
