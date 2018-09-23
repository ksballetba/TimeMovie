package com.ksballetba.timemovie.mvp.contract

import com.ksballetba.timemovie.base.BasePresenter
import com.ksballetba.timemovie.base.BaseView
import com.ksballetba.timemovie.mvp.model.bean.ComingMovieBean

interface ComingMovieContract{
    interface View: BaseView {
        fun setData(bean:ComingMovieBean)
    }
    interface Presenter: BasePresenter {
        fun requestData(locationId:String)
    }
}