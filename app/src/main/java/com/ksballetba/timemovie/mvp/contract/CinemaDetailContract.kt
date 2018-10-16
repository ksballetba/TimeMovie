package com.ksballetba.timemovie.mvp.contract

import com.ksballetba.timemovie.base.BasePresenter
import com.ksballetba.timemovie.base.BaseView
import com.ksballetba.timemovie.mvp.model.bean.CinemaDetailBean

interface CinemaDetailContract{
    interface View: BaseView {
        fun setData(bean:CinemaDetailBean?)
    }
    interface Presenter: BasePresenter {
        fun requestData(url:String)
    }
}