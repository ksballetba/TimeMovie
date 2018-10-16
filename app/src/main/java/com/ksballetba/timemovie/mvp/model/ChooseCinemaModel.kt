package com.ksballetba.timemovie.mvp.model

import com.ksballetba.timemovie.mvp.model.bean.CinemaBean
import com.ksballetba.timemovie.utils.getShowingCinemas

class ChooseCinemaModel{
    fun loadData(location:String,movieId:String,date:String):MutableList<CinemaBean>{
        return getShowingCinemas(location,movieId,date)
    }
}