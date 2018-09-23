package com.ksballetba.timemovie.ui.widgets

import android.support.design.widget.AppBarLayout

class AppBarStateChangeListener(internal val onStateChanged:(appBarLayout:AppBarLayout?,state:State)->Unit):AppBarLayout.OnOffsetChangedListener {

    enum class State{
        EXPANDED,
        COLLAPSED,
        IDLE
    }
    private var mCurrentState =State.IDLE
    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (verticalOffset == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED)
            }
            mCurrentState = State.EXPANDED
        } else if (Math.abs(verticalOffset) >= appBarLayout!!.totalScrollRange) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED)
            }
            mCurrentState = State.COLLAPSED
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE)
            }
            mCurrentState = State.IDLE
        }
    }

}