package com.ksballetba.timemovie.utils


import android.content.res.Resources
import android.support.design.widget.TabLayout
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import com.amap.api.navi.view.SlidingTabLayout
import com.bumptech.glide.load.engine.Resource
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.result.Result
import com.ksballetba.timemovie.api.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.padding
import java.lang.reflect.Field


fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return subscribeOn(Schedulers.io()).
            unsubscribeOn(Schedulers.io()).
            observeOn(AndroidSchedulers.mainThread())
}



fun setTabWidth(tabs: TabLayout,leftDip:Float,rightDip:Float){
    tabs.post {
        try {
            val tabLayout = tabs::class.java
            var tabStrip: Field? = null
            tabStrip = tabLayout.getDeclaredField("mTabStrip")
            tabStrip.isAccessible = true
            var llTab:LinearLayout? =null
            llTab = tabStrip.get(tabs) as LinearLayout
            val left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,leftDip, Resources.getSystem().displayMetrics)
            val right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,rightDip, Resources.getSystem().displayMetrics)
            for (i in 0 until llTab.childCount){
                val child = llTab.getChildAt(i)
                child.setPadding(0,0,0,0)
                val params = LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1f)
                params.leftMargin = left.toInt()
                params.rightMargin = right.toInt()
                child.layoutParams = params
                child.invalidate()
            }
        } catch (e:NoSuchFieldException){
            e.printStackTrace()
        } catch (e:IllegalAccessException){
            e.printStackTrace()
        }
    }
}

fun getDemo(locationId:String,movieId:String,complete: (demoStr: String?, error: FuelError?) -> Unit){
    FuelManager.instance.request(Method.GET, "https://ticket-api-m.mtime.cn/movie/detail.api", listOf(Pair("locationId",locationId),Pair("movieId",movieId)))
            .responseString(){request, response, result ->
                when(result){
                    is Result.Failure->{
                        complete(null,result.error)
                    }
                    is Result.Success->{
                        val(data,err) = result
                        complete(data!!,null)
                    }
                }
            }
}





