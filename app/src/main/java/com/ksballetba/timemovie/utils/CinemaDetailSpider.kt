package com.ksballetba.timemovie.utils

import android.util.Log
import com.google.gson.Gson
import com.ksballetba.timemovie.api.ApiService
import com.ksballetba.timemovie.mvp.model.bean.CinemaDetailBean
import org.json.JSONObject
import org.jsoup.Jsoup
import java.util.regex.Pattern


fun getCinemaDetail(url:String):CinemaDetailBean?{
    val url = url
    val doc = Jsoup.connect(url).get()
    val script = doc.getElementsByTag("script")[6]
    val raplaceArray = mutableListOf<String>()
    val patternStr = "new Date\\(\"([A-Za-z]*, [0-9]{1,2} [0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2})\"\\)"
    val pattern = Pattern.compile(patternStr)
    val matcher = pattern.matcher(script.data())
    while (matcher.find()) {
        raplaceArray.add("\"${matcher.group(1)}\"")
    }
    val newStr = matcher.replaceAll("kotlin")
    val sb = StringBuffer()
    val splitArray = newStr.split("kotlin")
    for(i in 0 until splitArray.size-1){
        sb.append(splitArray[i])
        sb.append(raplaceArray[i])
    }
    sb.append(splitArray[splitArray.size-1])
    val resultStr = sb.toString()
    val variables = resultStr.split("var")
    var cinemaDetail:CinemaDetailBean? = null
    for(variable in variables){
        if(variable.contains("cinemaShowtimesScriptVariables ")){
            var resultJsonStr = variable.replace("cinemaShowtimesScriptVariables = ","")
            cinemaDetail = Gson().fromJson(resultJsonStr,CinemaDetailBean::class.java)
            Log.d("debug",cinemaDetail.telphone)
        }
    }
    return cinemaDetail
}
