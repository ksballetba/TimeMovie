package com.ksballetba.timemovie.mvp.contract

import com.ksballetba.timemovie.base.BasePresenter
import com.ksballetba.timemovie.base.BaseView
import com.ksballetba.timemovie.mvp.model.bean.OrderBean


interface OrderContract{
    interface View: BaseView {
        fun setData(orderList:MutableList<OrderBean>)
    }
    interface Presenter: BasePresenter {
        fun requestData()
    }
}