package com.ksballetba.timemovie.mvp.model

import android.content.Context
import android.util.Log
import com.ksballetba.timemovie.api.ApiService
import com.ksballetba.timemovie.api.RetrofitClient
import com.ksballetba.timemovie.mvp.model.bean.MovieDetailBean
import io.reactivex.Observable

class MovieDetailModel {
    fun loadData(context: Context, locationId:String,movieId:String): Observable<MovieDetailBean>?{
        val retrofitClient = RetrofitClient.getInstanceDetail(context, ApiService.DETAIL_BASEURL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getMovieDetail(locationId,movieId)
    }

}