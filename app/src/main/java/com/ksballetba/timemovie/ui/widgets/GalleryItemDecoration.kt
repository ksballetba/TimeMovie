package com.ksballetba.timemovie.ui.widgets

import android.content.res.Resources
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class GalleryItemDecoration:RecyclerView.ItemDecoration(){
    var mPageMargin = 10
    var mLeftPageVisibleWidth = 136
    var mItemComsumX = 0


    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        val positon  = parent?.getChildAdapterPosition(view)
        val itemCount = parent?.adapter?.itemCount
        var itemNewWidth = parent!!.width - dpToPx(4*mPageMargin+2*mLeftPageVisibleWidth)
        mItemComsumX = itemNewWidth + dpToPx(2*mPageMargin)
        val leftMargin = if(positon==0) dpToPx(2*mPageMargin+mLeftPageVisibleWidth) else dpToPx(mPageMargin)
        val rightMargin = if(positon==(itemCount!!-1)) dpToPx(2*mPageMargin+mLeftPageVisibleWidth) else dpToPx(mPageMargin)
        val lp = view?.layoutParams as RecyclerView.LayoutParams
        lp.setMargins(leftMargin,30,rightMargin,60)
        view.layoutParams = lp
        super.getItemOffsets(outRect, view, parent, state)
    }



    private fun dpToPx(dp:Int):Int{
        return (dp*Resources.getSystem().displayMetrics.density+0.5f).toInt()
    }
}