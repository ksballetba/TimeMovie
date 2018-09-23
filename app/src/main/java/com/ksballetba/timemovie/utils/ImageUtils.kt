package com.ksballetba.timemovie.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.renderscript.*
import android.support.annotation.IntRange
import android.support.v7.graphics.Palette

class ImageUtils(val context: Context) {

    private var renderScript:RenderScript? = RenderScript.create(context)


    fun gaussianBlur(@IntRange(from = 1,to = 25)radius:Int,original:Bitmap):Bitmap{
        val input = Allocation.createFromBitmap(renderScript,original)
        val output = Allocation.createTyped(renderScript,input.type)
        val scriptInterinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        scriptInterinsicBlur.setRadius(radius.toFloat())
        scriptInterinsicBlur.setInput(input)
        scriptInterinsicBlur.forEach(output)
        output.copyTo(original)
        return original
    }


    fun getPalette(resourceId:Int,setColor:(palette:Palette)->Unit){
        val bitmap = BitmapFactory.decodeResource(context.resources,resourceId)
        val builder = Palette.from(bitmap)
        builder.generate{palette ->
            setColor(palette)
        }
    }

}