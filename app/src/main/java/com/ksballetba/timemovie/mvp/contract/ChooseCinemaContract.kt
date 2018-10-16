package com.ksballetba.timemovie.mvp.contract

import com.ksballetba.timemovie.base.BasePresenter
import com.ksballetba.timemovie.base.BaseView
import com.ksballetba.timemovie.mvp.model.bean.CinemaBean

interface ChooseCinemaContract{
    interface View:BaseView{
        fun setData(cinemaList:MutableList<CinemaBean>)
    }
    interface Presenter:BasePresenter{
        fun requestData(location:String,movieId:String,date:String)
    }
}