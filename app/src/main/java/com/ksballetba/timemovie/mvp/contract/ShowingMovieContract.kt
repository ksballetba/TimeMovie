package com.ksballetba.timemovie.mvp.contract

import com.ksballetba.timemovie.base.BasePresenter
import com.ksballetba.timemovie.base.BaseView
import com.ksballetba.timemovie.mvp.model.bean.ShowingMovieBean

interface ShowingMovieContract{
    interface View:BaseView{
        fun setData(bean:ShowingMovieBean)
    }
    interface Presenter:BasePresenter{
        fun requestData(locationId:String)
    }
}