package com.ksballetba.timemovie.mvp.contract

import com.ksballetba.timemovie.base.BasePresenter
import com.ksballetba.timemovie.base.BaseView
import com.ksballetba.timemovie.mvp.model.bean.BookingMovieBean

interface BookingMovieContract{
    interface View: BaseView {
        fun setData(bean:BookingMovieBean)
    }
    interface Presenter: BasePresenter {
        fun requestData(locationId:String)
    }
}