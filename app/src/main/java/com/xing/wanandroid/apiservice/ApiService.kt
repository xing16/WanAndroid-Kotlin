package com.xing.wanandroid.apiservice

import com.xing.wanandroid.base.BaseResponse
import com.xing.wanandroid.bean.ProjectTab
import com.xing.wanandroid.home.bean.Banner
import com.xing.wanandroid.home.bean.HomeRecommend
import com.xing.wanandroid.home.bean.HomeResponse
import com.xing.wanandroid.project.bean.ProjectResponse
import com.xing.wanandroid.search.bean.SearchHot
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("project/tree/json")
    fun getProjectTabs(): Observable<BaseResponse<List<ProjectTab>>>

    @GET("project/list/{page}/json")
    fun getProjectLists(@Path("page") page: Int, @Query("cid") cid: Int): Observable<BaseResponse<ProjectResponse>>

    @GET("banner/json")
    fun getBanner(): Observable<BaseResponse<List<Banner>>>

    @GET("article/list/{page}/json")
    fun getHomeRecommend(@Path("page") page: Int): Observable<BaseResponse<HomeResponse>>


    @GET("hotkey/json")
    fun getSearchHot(): Observable<BaseResponse<ArrayList<SearchHot>>>


}