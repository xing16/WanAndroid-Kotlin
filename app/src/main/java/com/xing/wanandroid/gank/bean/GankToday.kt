package com.xing.wanandroid.gank.bean


/**

{
"_id": "5c6a4ae99d212226776d3256",
"createdAt": "2019-02-18T06:04:25.571Z",
"desc": "2019-02-18",
"publishedAt": "2019-04-10T00:00:00.0Z",
"source": "web",
"type": "福利",
"url": "https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg",
"used": true,
"who": "lijinshanmx"
}




 *
 */

data class GankToday(
    val desc: String,
    val publishedAt: String,
    val type: String,
    val url: String,
    val who: String,
    val images: ArrayList<String>?
)