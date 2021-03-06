package com.ksballetba.timemovie.utils


import android.app.Activity
import android.content.res.Resources
import android.os.Build
import android.support.design.widget.TabLayout
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import com.amap.api.navi.view.SlidingTabLayout
import com.bumptech.glide.load.engine.Resource
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
            var tabStrip: Field?
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

fun isActivityDestroyed(activity: Activity):Boolean{
    if (activity == null || activity.isFinishing ) {
        return true
    } else {
        return false
    }

}





