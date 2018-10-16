package com.ksballetba.timemovie.mvp.presenter

import android.content.Context
import com.ksballetba.timemovie.mvp.contract.CinemaContract
import com.ksballetba.timemovie.mvp.model.CinemaModel

class CinemaPresenter(context: Context?, view: CinemaContract.View) : CinemaContract.Presenter {
    var mContext: Context? = null
    var mView: CinemaContract.View? = null
    val mModel: CinemaModel by lazy {
        CinemaModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {
    }

    override fun requestData(location: String) {
        val cinemaList = mModel.loadData(location)
        mView?.setData(cinemaList)
    }
}