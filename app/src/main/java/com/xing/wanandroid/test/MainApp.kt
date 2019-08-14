package com.xing.wanandroid.test

fun main(args: Array<String>) {

    val man = Man("雷军")
    val woman = Woman("宋慧乔")
    man.eat()
    woman.eat()

    man.pee()
    woman.pee()

    val animal = Dog("tomcat")
    animal.run()

    var list = mutableListOf(12, 3, 4, 5)
    list.testExpand(1, 2)
    println(list)

    var strList = mutableListOf("tomcat", "linux", "apple", "mac")
    strList.testExpand(1, 2)
    println(strList)

    /**
     * 当扩展函数和成员函数签名相同时，调用时总是调用 成员函数
     */
    var expand = Expand()
    expand.foo() // member function
    expand.foo(1)  // expand function params = 1


}


/**
 * 1. String  为不可为空类型，所以赋值为 null 会编译不过, 可以直接调用成员变量或方法
 * 2. String? 为可空类型，所以赋值为 null 能够编译通过，直接调用成员变量或方法会编译不过(相当于
 * 在语法层面上做出了限制)，需要带上问号 ？，
 */
fun testNull() {

    // String
    var name: String = "tomcat"
    var nameLength = name.length
//    name = null    // 编译不过


    // String?
    var address: String? = "上海"
//    var length = address.length   // 编译不过，需要带上问号
    var length = address?.length
    var res = if (address != null) address.length else -1
    address = null


}

/**
 * 扩展函数
 */
fun <T> MutableList<T>.testExpand(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}


/**
 * Expand 类的扩展函数
 */
fun Expand.foo() {
    println("expand function")
}

fun Expand.foo(index: Int) {
    println("expand function params = $index")
}







