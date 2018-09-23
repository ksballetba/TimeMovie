package com.ksballetba.timemovie.mvp.presenter

import android.content.Context
import com.ksballetba.timemovie.mvp.contract.ComingMovieContract
import com.ksballetba.timemovie.mvp.model.ComingMovieModel
import com.ksballetba.timemovie.mvp.model.bean.ComingMovieBean
import com.ksballetba.timemovie.utils.applySchedulers
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.toast

class ComingMoviePresenter(context: Context?,view: ComingMovieContract.View):ComingMovieContract.Presenter {
    var mContext:Context?=null
    var mView: ComingMovieContract.View?=null
    val mModel: ComingMovieModel by lazy {
        ComingMovieModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {
    }

    override fun requestData(locationId:String) {
        val observable: Observable<ComingMovieBean>?=mContext?.let {
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
        }
        )
    }
}