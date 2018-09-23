package com.ksballetba.timemovie.ui.behavior

import android.animation.ObjectAnimator
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View

class BottomNavigationBehavior:CoordinatorLayout.Behavior<View> {

    private lateinit var inAnimator:ObjectAnimator
    private lateinit var outAnimator:ObjectAnimator

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if(dy>0){
            if(!::outAnimator.isInitialized){
                outAnimator = ObjectAnimator.ofFloat(child,"translationY",0f,child.height.toFloat())
                outAnimator.setDuration(200)
            }
            if(!outAnimator.isRunning&&child.translationY<=0){
                outAnimator.start()
            }
        } else if (dy<0){
            if(!::inAnimator.isInitialized){
                inAnimator = ObjectAnimator.ofFloat(child,"translationY",child.height.toFloat(),0f)
                inAnimator.setDuration(200)
            }
            if(!inAnimator.isRunning&&child.translationY>=child.height){
                inAnimator.start()
            }
        }
    }

}