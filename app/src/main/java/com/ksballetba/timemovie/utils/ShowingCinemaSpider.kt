package com.ksballetba.timemovie.utils

import android.util.Log
import com.google.gson.Gson
import com.ksballetba.timemovie.api.ApiService
import com.ksballetba.timemovie.mvp.model.bean.CinemaBean
import org.json.JSONArray
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*

fun getShowingCinemas(location:String,movieId:String,date:String):MutableList<CinemaBean>{
    val url = "${ApiService.THEATER_BASEURL}$location/movie/$movieId/$date/"
    val doc = Jsoup.connect(url).get()
    val script = doc.getElementsByTag("script")[4]
    var cinemaList = mutableListOf<CinemaBean>()
    val variables = script.data().toString().split("var")
    var cinemaJson:String?
    for (variable in variables){
        if (variable.contains("cinemasJson")){
            cinemaJson = variable.split("=")[1]
            val cinemaJsonArray = JSONArray(cinemaJson)
            for(i in 0 until cinemaJsonArray.length()){
                val cinema = Gson().fromJson<CinemaBean>(cinemaJsonArray[i].toString(),CinemaBean::class.java)
                cinemaList.add(cinema)
            }
        }
    }
    return cinemaList
}

fun getCurrentDate():String{
    val dateFormat = SimpleDateFormat("yyyyMMdd")
    val date = Date(System.currentTimeMillis())
    return dateFormat.format(date)
}

fun parseStringToDate(date: String):String{
    if(date[4]=='0'){
        return date.substring(5,6)+"月"+date.substring(6,8)+"日"
    } else{
        return date.substring(4,6)+"月"+date.substring(6,8)+"日"
    }
}

fun getAfterDate(currentDate:String,plusDays:Int):String{
    val month = currentDate.substring(4,6).toInt()
    val day = currentDate.substring(6,8).toInt()
    when(month){
        1,3,5,7,8,10,12->{
            if(day+plusDays<=31){
                return (currentDate.toInt()+plusDays).toString()
            } else{
                return currentDate.substring(0,4)+(month+1).toString()+"0"+(day+plusDays-31).toString()
            }
        }
        4,6,9,11->{
            if(day+plusDays<=30){
                return (currentDate.toInt()+plusDays).toString()
            } else{
                return currentDate.substring(0,4)+(month+1).toString()+"0"+(day+plusDays-30).toString()
            }
        }
        2->{
            if(day+plusDays<=28){
                return (currentDate.toInt()+plusDays).toString()
            } else{
                return currentDate.substring(0,4)+(month+1).toString()+"0"+(day+plusDays-28).toString()
            }
        }
    }
    return "20181001"
}