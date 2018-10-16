package com.ksballetba.timemovie.ui.widgets

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView

class GalleryScrollManager(val mRecyclerView: RecyclerView,internal val updateInfoAtPos:(idx:Int)->Unit){
    var mPosition = 0
    var mComsumeX = 0
    val shouldComsumeX = 420
    val mScale = 0.2f
    fun initScroolListenner(){
        mRecyclerView.addOnScrollListener(GalleryScrollerListener())
    }

    inner class GalleryScrollerListener:RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            onHorizontalScroll(dx)
            super.onScrolled(recyclerView, dx, dy)
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            if(newState==0){
                updateInfoAtPos(mPosition)
            }
            super.onScrollStateChanged(recyclerView, newState)
        }
    }

    private fun onHorizontalScroll(dx:Int){
        mComsumeX+=dx
        val offset = mComsumeX.toFloat()/shouldComsumeX.toFloat()
        mPosition = offset.toInt()
    }
}