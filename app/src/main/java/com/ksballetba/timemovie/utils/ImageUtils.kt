package com.ksballetba.timemovie.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.renderscript.*
import android.support.annotation.IntRange
import android.support.v7.graphics.Palette

private val BITMAP_SCALE = 0.4f

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

fun dpToPx(dp:Int):Int{
    return (dp* Resources.getSystem().displayMetrics.density+0.5f).toInt()
}



fun blurBitmap(context: Context, image: Bitmap, @IntRange(from = 1,to = 25)blurRadius: Float): Bitmap {
    // 计算图片缩小后的长宽
    val width = Math.round(image.width * BITMAP_SCALE)
    val height = Math.round(image.height * BITMAP_SCALE)

    // 将缩小后的图片做为预渲染的图片
    val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
    // 创建一张渲染后的输出图片
    val outputBitmap = Bitmap.createBitmap(inputBitmap)

    // 创建RenderScript内核对象
    val rs = RenderScript.create(context)
    // 创建一个模糊效果的RenderScript的工具对象
    val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

    // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
    // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
    val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
    val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)

    // 设置渲染的模糊程度, 25f是最大模糊度
    blurScript.setRadius(blurRadius)
    // 设置blurScript对象的输入内存
    blurScript.setInput(tmpIn)
    // 将输出数据保存到输出内存中
    blurScript.forEach(tmpOut)

    // 将数据填充到Allocation中
    tmpOut.copyTo(outputBitmap)

    return outputBitmap
}

