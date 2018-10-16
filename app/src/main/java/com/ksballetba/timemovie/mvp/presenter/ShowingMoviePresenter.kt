package com.ksballetba.timemovie.mvp.presenter

import android.content.Context
import android.util.Log
import com.ksballetba.timemovie.mvp.contract.ShowingMovieContract
import com.ksballetba.timemovie.mvp.model.ShowingMovieModel
import com.ksballetba.timemovie.mvp.model.bean.ShowingMovieBean
import com.ksballetba.timemovie.utils.applySchedulers
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.toast

class ShowingMoviePresenter(context: Context?,view:ShowingMovieContract.View):ShowingMovieContract.Presenter{

    var mContext:Context?=null
    var mView:ShowingMovieContract.View?=null
    val mModel:ShowingMovieModel by lazy {
        ShowingMovieModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {
    }

    override fun requestData(locationId:String) {
        val observable:Observable<ShowingMovieBean>?=mContext?.let {
            mModel.loadData(it,locationId)
        }
        observable?.applySchedulers()?.subscribeBy(
                onNext = {
                    mView?.setData(it)
                },
                onComplete = {
                    println("Completed")
                },
                onError = {
                    mContext?.toast("网络连接失败，请连接后重试")
                    Log.d("debug",it.message)
                }
        )
    }
}