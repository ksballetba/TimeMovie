package com.ksballetba.timemovie.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.ui.fragments.SignInFragment
import com.ksballetba.timemovie.ui.fragments.SignUpFragment
import com.ksballetba.timemovie.ui.widgets.CardPageTransformer
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*

class LoginActivity : AppCompatActivity() {

    lateinit var signInFragment: SignInFragment
    lateinit var signUpFragment: SignUpFragment
    var fragmentList = ArrayList<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initFragments()
    }

    private fun initFragments() {
        signInFragment = SignInFragment()
        signUpFragment = SignUpFragment()
        fragmentList.add(signInFragment)
        fragmentList.add(signUpFragment)
        login_viewpager.offscreenPageLimit = 2
        login_viewpager.adapter = KotlinPagerAdapter(fragmentList,supportFragmentManager)
        login_viewpager.setPageTransformer(true,CardPageTransformer())

    }

    fun showSignUp() {
       login_viewpager.currentItem = 1
    }

    fun showSignIn() {
      login_viewpager.currentItem=0
    }
}
