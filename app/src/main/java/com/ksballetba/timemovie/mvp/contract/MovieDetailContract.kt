package com.ksballetba.timemovie.mvp.contract

import com.ksballetba.timemovie.base.BasePresenter
import com.ksballetba.timemovie.base.BaseView
import com.ksballetba.timemovie.mvp.model.bean.MovieDetailBean


interface MovieDetailContract{
    interface View: BaseView {
        fun setData(bean:MovieDetailBean)
    }
    interface Presenter: BasePresenter {
        fun requestData(locationId:String,movieId:String)
    }
}