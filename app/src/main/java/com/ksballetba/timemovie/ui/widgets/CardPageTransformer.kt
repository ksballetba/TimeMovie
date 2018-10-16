package com.ksballetba.timemovie.ui.widgets

import android.support.v4.view.ViewPager
import android.view.View

class CardPageTransformer:ViewPager.PageTransformer{
    val MIN_SCALE = 0.85f
    val MIN_ALPHA = 0.85f

    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width
        val pageHeight = page.height

        if(position<-1){
            page.alpha = 0f
        } else if(position<=1){
            val scaleFactor = Math.max(MIN_SCALE,1-Math.abs(position))
            val vertMargin = pageHeight*(1-scaleFactor)/2
            val horzMargin = pageWidth*(1-scaleFactor)/2
            if(position<0){
                page.translationX = horzMargin - vertMargin/2
            }else{
                page.translationX =-horzMargin+vertMargin/2
            }

            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
            page.alpha = MIN_ALPHA+(scaleFactor-MIN_SCALE)/(1-MIN_SCALE)*(1-MIN_ALPHA)
        } else{
            page.alpha = 0f
        }
    }
}