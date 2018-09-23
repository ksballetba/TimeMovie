package com.ksballetba.timemovie.mvp.presenter

import android.content.Context
import com.ksballetba.timemovie.mvp.contract.BookingMovieContract
import com.ksballetba.timemovie.mvp.model.BookingMovieModel
import com.ksballetba.timemovie.mvp.model.bean.BookingMovieBean
import com.ksballetba.timemovie.utils.applySchedulers
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.toast

class BookingMoviePresenter(context: Context?, view: BookingMovieContract.View) : BookingMovieContract.Presenter {
    var mContext: Context? = null
    var mView: BookingMovieContract.View? = null
    val mModel: BookingMovieModel by lazy {
        BookingMovieModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {
    }

    override fun requestData(locationId: String) {
        val observable: Observable<BookingMovieBean>? = mContext?.let {
            mModel.loadData(it, locationId)
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