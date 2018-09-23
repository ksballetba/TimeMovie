package com.ksballetba.timemovie.mvp.presenter

import android.content.Context
import android.util.Log
import com.ksballetba.timemovie.mvp.contract.MovieDetailContract
import com.ksballetba.timemovie.mvp.model.MovieDetailModel
import com.ksballetba.timemovie.mvp.model.bean.MovieDetailBean
import com.ksballetba.timemovie.utils.applySchedulers
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast

class MovieDetailPresenter(context: Context?,view:MovieDetailContract.View):MovieDetailContract.Presenter{

    var mContext: Context?=null
    var mView:MovieDetailContract.View?=null
    val mModel:MovieDetailModel by lazy {
        MovieDetailModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {
    }

    override fun requestData(locationId:String,movieId:String) {
        val observable: Observable<MovieDetailBean>?=mContext?.let {
            mModel.loadData(it,locationId,movieId)
        }

        observable?.subscribeOn(Schedulers.newThread())?.observeOn(AndroidSchedulers.mainThread())?.subscribeBy(
                onNext = {
                    mView?.setData(it)
                },
                onComplete = {
                    println("Completed")
                },
                onError = {
                    Log.d("debug",it.message)
                    mContext?.toast("网络连接失败，请连接后重试")
                }
        )
    }
}