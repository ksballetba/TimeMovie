package com.ksballetba.timemovie.api

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient private constructor(context: Context,baseUrl:String){
    val mContext = context
    lateinit var okHttpClient:OkHttpClient
    var retrofit:Retrofit? = null
    val DEFAULT_TIMEOUT:Long = 20
    val url = baseUrl

    init {
        okHttpClient = OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .build()
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build()
    }

    companion object {
        @Volatile
        var instance:RetrofitClient? = null
        var instanceDetail:RetrofitClient? = null

        fun getInstance(context: Context,baseUrl: String):RetrofitClient{
            if(instance==null){
                synchronized(RetrofitClient::class){
                    if (instance==null){
                        instance = RetrofitClient(context,baseUrl)
                    }
                }
            }
            return instance!!
        }

        fun getInstanceDetail(context: Context,baseUrl: String):RetrofitClient{
            if(instanceDetail==null){
                synchronized(RetrofitClient::class){
                    if (instanceDetail==null){
                        instanceDetail = RetrofitClient(context,baseUrl)
                    }
                }
            }
            return instanceDetail!!
        }
    }

    fun <T> create(service: Class<T>?): T? {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit?.create(service)
    }

}