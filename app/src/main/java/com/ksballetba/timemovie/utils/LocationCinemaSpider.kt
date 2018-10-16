package com.ksballetba.timemovie.utils

import android.util.Log
import com.github.promeg.pinyinhelper.Pinyin
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict
import com.google.gson.Gson
import com.ksballetba.timemovie.api.ApiService
import com.ksballetba.timemovie.mvp.model.bean.CinemaBean
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup

fun getLocaitonCinemas(location:String):MutableList<CinemaBean>{
    val url = "${ApiService.THEATER_BASEURL}$location/"
    val doc = Jsoup.connect(url).get()
    val script = doc.getElementsByTag("script")[5]
    var cinemaList = mutableListOf<CinemaBean>()
    val variables = script.data().toString().split("var")
    var cinemaJsonStr:String?
    for (variable in variables){
        if (variable.contains("cinemasJson")){
            cinemaJsonStr = variable.split("=")[1]
            val cinemaJsonAll = JSONObject(cinemaJsonStr)
            val cinemaJsonArray = cinemaJsonAll.getJSONArray("list")
            for(i in 0 until cinemaJsonArray.length()){
                val cinema = Gson().fromJson<CinemaBean>(cinemaJsonArray[i].toString(), CinemaBean::class.java)
                cinemaList.add(cinema)
            }
        }
    }
    return cinemaList
}

