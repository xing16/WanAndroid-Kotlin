package com.xing.wanandroid.apiservice

import com.tencent.mm.opensdk.modelbase.BaseResp
import com.xing.wanandroid.base.BaseResponse
import com.xing.wanandroid.db.bean.User
import com.xing.wanandroid.gank.bean.GankToday
import com.xing.wanandroid.gank.bean.WxPublic
import com.xing.wanandroid.home.bean.Article
import com.xing.wanandroid.home.bean.ArticleResponse
import com.xing.wanandroid.home.bean.Banner
import com.xing.wanandroid.meizi.bean.Meizi
import com.xing.wanandroid.project.bean.ProjectResponse
import com.xing.wanandroid.project.bean.ProjectTab
import com.xing.wanandroid.search.bean.SearchHot
import com.xing.wanandroid.search.bean.SearchResultResponse
import com.xing.wanandroid.setting.bean.LogoutResult
import com.xing.wanandroid.system.bean.SystemCategory
import com.xing.wanandroid.user.bean.LoginResponse
import com.xing.wanandroid.user.bean.RegisterResponse
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
    fun getTopArticle(): Observable<BaseResponse<List<Article>>>

    @GET("article/list/{page}/json")
    fun getArticles(@Path("page") page: Int): Observable<BaseResponse<ArticleResponse>>

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

    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<BaseResponse<User>>

    @POST("user/register")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): Observable<BaseResponse<RegisterResponse>>


    @GET("tree/json")
    fun getSystem(): Observable<BaseResponse<List<SystemCategory>>>

    @GET("article/list/{page}/json")
    fun getSystemArticles(@Path("page") page: Int, @Query("cid") cid: Int): Observable<BaseResponse<ArticleResponse>>

    @GET("lg/collect/list/{page}/json")
    fun getArticleFavorites(@Path("page") page: Int): Observable<BaseResponse<ArticleResponse>>

    @GET("wxarticle/chapters/json")
    fun getWxPublic(): Observable<BaseResponse<List<WxPublic>>>


    @GET("http://gank.io/api/today")
    fun getGankToday(): Observable<BaseResponse<HashMap<String, List<GankToday>>>>

    @GET("wxarticle/list/{id}/{page}/json")
    fun getWxPublicArticle(@Path("id") id: Int, @Path("page") page: Int): Observable<BaseResponse<ArticleResponse>>


    @GET("user/logout/json")
    fun logout(): Observable<BaseResponse<LogoutResult>>


}