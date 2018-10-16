package com.ksballetba.timemovie.mvp.presenter

import android.content.Context
import com.ksballetba.timemovie.mvp.contract.CinemaDetailContract
import com.ksballetba.timemovie.mvp.model.CinemaDetailModel

class CinemaDetailPresenter(context: Context?, view: CinemaDetailContract.View) : CinemaDetailContract.Presenter {
    var mContext: Context? = null
    var mView: CinemaDetailContract.View? = null
    val mModel: CinemaDetailModel by lazy {
        CinemaDetailModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {
    }

    override fun requestData(url:String) {
        val cinemaDetail = mModel.loadData(url)
        mView?.setData(cinemaDetail)
    }
}