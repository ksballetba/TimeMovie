package com.ksballetba.timemovie.mvp.model

import com.ksballetba.timemovie.mvp.model.bean.OrderBean
import org.litepal.LitePal
import org.litepal.extension.findAll

class OrderModel {
    fun loadData():MutableList<OrderBean>{
        val orderList = LitePal.findAll<OrderBean>().toMutableList()
        return orderList
    }
}