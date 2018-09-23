package com.ksballetba.timemovie.mvp.model

import android.content.Context
import com.ksballetba.timemovie.api.ApiService
import com.ksballetba.timemovie.api.RetrofitClient
import com.ksballetba.timemovie.mvp.model.bean.MovieCommentsBean
import io.reactivex.Observable

class MovieCommentsModel {
    fun loadData(context: Context, movieId:String): Observable<MovieCommentsBean>?{
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.MOVIE_BASEURL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getMovieComments(movieId)
    }
}