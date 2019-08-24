package com.xing.wanandroid.apiservice

import com.xing.wanandroid.base.BaseResponse
import com.xing.wanandroid.bean.ProjectTab
import com.xing.wanandroid.home.bean.Banner
import com.xing.wanandroid.home.bean.HomeArticle
import com.xing.wanandroid.home.bean.HomeResponse
import com.xing.wanandroid.meizi.bean.Meizi
import com.xing.wanandroid.project.bean.ProjectResponse
import com.xing.wanandroid.search.bean.SearchHot
import com.xing.wanandroid.search.bean.SearchResultResponse
import com.xing.wanandroid.web.bean.AddFavoriteResponse
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {


    @GET("project/tree/json")
    fun getProjectTabs(): Observable<BaseResponse<List<ProjectTab>>>

    @GET("project/list/{page}/json")
    fun getProjectLists(@Path("page") page: Int, @Query("cid") cid: Int): Observable<BaseResponse<ProjectResponse>>

    @GET("banner/json")
    fun getBanner(): Observable<BaseResponse<List<Banner>>>

    @GET("article/top/json")
    fun getTopArticle(): Observable<BaseResponse<List<HomeArticle>>>

    @GET("article/list/{page}/json")
    fun getArticles(@Path("page") page: Int): Observable<BaseResponse<HomeResponse>>

    @GET("hotkey/json")
    fun getSearchHot(): Observable<BaseResponse<ArrayList<SearchHot>>>

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    fun getSearchResult(@Path("page") page: Int, @Field("k") keyword: String): Observable<BaseResponse<SearchResultResponse>>

    @GET("http://gank.io/api/data/福利/{pageSize}/{page}")
    fun getMeiziList(@Path("pageSize") pageSize: Int, @Path("page") page: Int): Observable<BaseResponse<List<Meizi>>>


    @POST("lg/collect/{id}/json")
    fun addFavorite(@Path("id") id: Int): Observable<BaseResponse<AddFavoriteResponse>>


    @POST("lg/collect/add/json")
    @FormUrlEncoded
    fun addFavorite(@Field("title") title: String, @Field("author") author: String, @Field("link") link: String): Observable<BaseResponse<AddFavoriteResponse>>


}