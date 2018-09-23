package com.ksballetba.timemovie.mvp.presenter

import android.content.Context
import com.ksballetba.timemovie.mvp.contract.MovieCommentsContract
import com.ksballetba.timemovie.mvp.model.MovieCommentsModel
import com.ksballetba.timemovie.mvp.model.bean.MovieCommentsBean
import com.ksballetba.timemovie.utils.applySchedulers
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.toast

class MovieCommentsPresenter(context: Context?,view: MovieCommentsContract.View): MovieCommentsContract.Presenter {
    var mContext: Context?=null
    var mView: MovieCommentsContract.View?=null
    val mModel: MovieCommentsModel by lazy {
        MovieCommentsModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {
    }

    override fun requestData(movieId:String) {
        val observable: Observable<MovieCommentsBean>?=mContext?.let {
            mModel.loadData(it,movieId)
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