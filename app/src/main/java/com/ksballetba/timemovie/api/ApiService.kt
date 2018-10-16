package com.ksballetba.timemovie.api

import com.ksballetba.timemovie.mvp.model.bean.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

//正在售票   https://api-m.mtime.cn/PageSubArea/HotPlayMovies.api?locationId=290
//正在热映   https://api-m.mtime.cn/Showtime/LocationMovies.api?locationId=290
//即将上映   https://api-m.mtime.cn/Movie/MovieComingNew.api?locationId=290
//演职员表   https://api-m.mtime.cn/Movie/MovieCreditsWithTypes.api?movieId=217896
//预告片啦   https://api-m.mtime.cn/Movie/Video.api?pageIndex=1&movieId=217896
//剧照啦啦   https://api-m.mtime.cn/Movie/ImageAll.api?movieId=217896

//影片评论   https://api-m.mtime.cn/Showtime/HotMovieComments.api?movieId=217896
//影片详情   https://ticket-api-m.mtime.cn/movie/detail.api?locationId=290&movieId=125805

interface ApiService{
    companion object {
        val MOVIE_BASEURL="https://api-m.mtime.cn/"
        val DETAIL_BASEURL=" https://ticket-api-m.mtime.cn/movie/"
        val THEATER_BASEURL = "http://theater.mtime.com/"
    }

    @GET("PageSubArea/HotPlayMovies.api")
    fun getBookingMovie(@Query("locationId") locationId:String):Observable<BookingMovieBean>

    @GET("Showtime/LocationMovies.api")
    fun getShowingMovie(@Query("locationId") locationId:String):Observable<ShowingMovieBean>

    @GET("Movie/MovieComingNew.api")
    fun getComingMovie(@Query("locationId") locationId:String):Observable<ComingMovieBean>

    @GET("Movie/MovieCreditsWithTypes.api")
    fun getMovieActor(@Query("movieId") movieId:String):Observable<MovieActorBean>

    @GET("Movie/Video.api")
    fun getMovieVideo(@Query("movieId") movieId:String):Observable<MovieVideoBean>

    @GET("Movie/ImageAll.api")
    fun getMovieImage(@Query("movieId") movieId:String):Observable<MoviePicBean>

    @GET("Showtime/HotMovieComments.api")
    fun getMovieComments(@Query("movieId") movieId:String):Observable<MovieCommentsBean>

    @GET("detail.api")
    fun getMovieDetail(@Query("locationId") locationId: String, @Query("movieId") movieId:String):Observable<MovieDetailBean>

}