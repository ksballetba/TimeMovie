package com.ksballetba.timemovie.mvp.model

import com.ksballetba.timemovie.mvp.model.bean.CinemaBean
import com.ksballetba.timemovie.utils.getLocaitonCinemas

class CinemaModel{
    fun loadData(location:String):MutableList<CinemaBean>{
        return getLocaitonCinemas(location)
    }
}