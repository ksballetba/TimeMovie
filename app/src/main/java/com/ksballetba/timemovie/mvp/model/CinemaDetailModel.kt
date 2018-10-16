package com.ksballetba.timemovie.mvp.model

import com.ksballetba.timemovie.mvp.model.bean.CinemaDetailBean
import com.ksballetba.timemovie.utils.getCinemaDetail

class CinemaDetailModel{
    fun loadData(url:String):CinemaDetailBean?{
        return getCinemaDetail(url)
    }
}