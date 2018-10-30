package com.ksballetba.timemovie.ui.widgets

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import android.util.DisplayMetrics



class GalleryItemDecoration(val mContext:Context) : RecyclerView.ItemDecoration() {

    var mPageMargin = 10 //自定义默认item边距
    var mLeftPageVisibleWidth = (pxToDp(mContext.resources.displayMetrics .widthPixels)-100)/2
    //第一张图片的左边距

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        val positon = parent?.getChildAdapterPosition(view) //获得当前item的position
        val itemCount = parent?.adapter?.itemCount //获得item的数量
        val leftMargin = if (positon == 0) dpToPx(mLeftPageVisibleWidth) else dpToPx(mPageMargin) //如果position为0，设置leftMargin为计算后边距，其他为默认边距
        val rightMargin = if (positon == (itemCount!! - 1)) dpToPx(mLeftPageVisibleWidth) else dpToPx(mPageMargin) //同上，设置最后一张图片
        val lp = view?.layoutParams as RecyclerView.LayoutParams
        lp.setMargins(leftMargin, 30, rightMargin, 60) //30和60分别是item到上下的margin
        view.layoutParams = lp //设置参数
        super.getItemOffsets(outRect, view, parent, state)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt() //dp转px
    }

    private fun pxToDp(px: Int): Int {
        return Math.round(px / (Resources.getSystem().displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }
}