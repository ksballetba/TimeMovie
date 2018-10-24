package com.ksballetba.timemovie.mvp.presenter

import android.content.Context
import com.ksballetba.timemovie.mvp.contract.OrderContract
import com.ksballetba.timemovie.mvp.model.OrderModel

class OrderPresenter(context: Context?, view: OrderContract.View) : OrderContract.Presenter {
    var mContext: Context? = null
    var mView: OrderContract.View? = null
    val mModel: OrderModel by lazy {
        OrderModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {
    }

    override fun requestData() {
        val orderList = mModel.loadData()
        mView?.setData(orderList)
    }
}