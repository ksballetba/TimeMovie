package com.ksballetba.timemovie.mvp.model

import android.content.Context
import com.ksballetba.timemovie.api.ApiService
import com.ksballetba.timemovie.api.RetrofitClient
import com.ksballetba.timemovie.mvp.model.bean.ComingMovieBean
import io.reactivex.Observable

class ComingMovieModel {
    fun loadData(context: Context, locationId:String): Observable<ComingMovieBean>?{
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.MOVIE_BASEURL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getComingMovie(locationId)
    }
}