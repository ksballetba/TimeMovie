package com.ksballetba.timemovie.mvp.presenter

import android.content.Context
import com.ksballetba.timemovie.mvp.contract.ChooseCinemaContract
import com.ksballetba.timemovie.mvp.model.ChooseCinemaModel

class ChooseCinemaPresenter(context: Context?, view: ChooseCinemaContract.View) : ChooseCinemaContract.Presenter {
    var mContext: Context? = null
    var mView: ChooseCinemaContract.View? = null
    val mModel: ChooseCinemaModel by lazy {
        ChooseCinemaModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {
    }

    override fun requestData(location: String,movieId:String,date:String) {
        val cinemaList = mModel.loadData(location,movieId,date)
        mView?.setData(cinemaList)
    }
}