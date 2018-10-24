package com.ksballetba.timemovie.ui.behavior

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View


class FabBehavior : CoordinatorLayout.Behavior<View> {

    private lateinit var inAnimatorX: ObjectAnimator
    private lateinit var outAnimatorX: ObjectAnimator
    private lateinit var inAnimatorY: ObjectAnimator
    private lateinit var outAnimatorY: ObjectAnimator
    private lateinit var inSet: AnimatorSet
    private lateinit var outSet: AnimatorSet

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (dy > 0) {
            if (!::inSet.isInitialized) {
                inSet = AnimatorSet()
                inAnimatorX = ObjectAnimator.ofFloat(child, "scaleX", 1f, 0f)
                inAnimatorY = ObjectAnimator.ofFloat(child, "scaleY", 1f, 0f)
                inSet.playTogether(inAnimatorX,inAnimatorY)
                inSet.duration = 200
            }
            if (!inSet.isRunning && child.scaleX >= 1f) {
                inSet.start()
            }
        } else if (dy < 0) {
            if (!::outSet.isInitialized) {
                outSet = AnimatorSet()
                outAnimatorX = ObjectAnimator.ofFloat(child, "scaleX", 0f, 1f)
                outAnimatorY = ObjectAnimator.ofFloat(child, "scaleY", 0f, 1f)
                outSet.playTogether(outAnimatorX, outAnimatorY)
                outSet.duration = 200
            }
            if (!outSet.isRunning && child.scaleX <= 0) {
                outSet.start()
            }
        }
    }

}