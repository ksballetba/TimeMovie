package com.ksballetba.timemovie.mvp.contract

import com.ksballetba.timemovie.base.BasePresenter
import com.ksballetba.timemovie.base.BaseView
import com.ksballetba.timemovie.mvp.model.bean.MovieCommentsBean

interface MovieCommentsContract{
    interface View: BaseView {
        fun setData(bean: MovieCommentsBean)
    }
    interface Presenter: BasePresenter {
        fun requestData(movieId:String)
    }
}