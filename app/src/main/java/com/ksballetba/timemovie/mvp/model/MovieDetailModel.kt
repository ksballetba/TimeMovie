package com.ksballetba.timemovie.mvp.model

import android.content.Context
import android.util.Log
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.result.Result
import com.ksballetba.timemovie.api.ApiService
import com.ksballetba.timemovie.api.RetrofitClient
import com.ksballetba.timemovie.mvp.model.bean.MovieDetailBean
import io.reactivex.Observable

class MovieDetailModel {
    fun loadData(context: Context, locationId:String,movieId:String): Observable<MovieDetailBean>?{
        var observable = Observable.create<MovieDetailBean>{
            getMovieDetail(locationId,movieId){movieDetailBean, error ->
                if(error==null){
                    it.onNext(movieDetailBean!!)
                    it.onComplete()
                } else{
                    Log.d("debug",error.message)
                }
            }
        }
        return observable
    }


    fun getMovieDetail(locationId:String,movieId:String,complete: (movieDetailBean: MovieDetailBean?, error: FuelError?) -> Unit){
        FuelManager.instance.request(Method.GET, "https://ticket-api-m.mtime.cn/movie/detail.api", listOf(Pair("locationId",locationId),Pair("movieId",movieId)))
                .responseObject(MovieDetailBean.Deserializer()){request, response, result ->
                    when(result){
                        is Result.Failure->{
                            complete(null,result.error)
                        }
                        is Result.Success->{
                            val(data,err) = result
                            complete(data!!,null)
                        }
                    }
                }
    }

}