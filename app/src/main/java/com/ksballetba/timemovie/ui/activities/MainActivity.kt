package com.ksballetba.timemovie.ui.activities

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.WindowManager
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.ui.fragments.AccountFragment
import com.ksballetba.timemovie.ui.fragments.HomeFragment
import com.ksballetba.timemovie.ui.fragments.MovieFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_nav_head.*
import org.jetbrains.anko.toast
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val PERMISSISON_CODE = 100
    }

    val fragmentList = ArrayList<Fragment>()
    lateinit var homeFragment: HomeFragment
    lateinit var movieFragment: MovieFragment
    lateinit var accountFragment: AccountFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        super.onCreate(savedInstanceState)
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        setContentView(R.layout.activity_main)
        initToolbar()
        initFragments()
    }

    private fun initToolbar(){
        setSupportActionBar(main_toolbar)
        supportActionBar?.title = "首页"
        main_bnv.setOnNavigationItemSelectedListener {MenuItem->
            when (MenuItem.itemId) {
                R.id.menu_home->{
//                    MenuItem.isChecked = true
                    supportActionBar?.title = "首页"
                    main_viewpager.currentItem = 0
                }
                R.id.menu_movie->{
//                    MenuItem.isChecked = true
                    supportActionBar?.title = "电影"
                    main_viewpager.currentItem = 1
                }
                R.id.menu_account->{
//                    MenuItem.isChecked = true
                    supportActionBar?.title = "影院"
                    main_viewpager.currentItem = 2
                }
            }
            true
        }
    }

    private fun initFragments(){
        homeFragment = HomeFragment()
        movieFragment = MovieFragment()
        accountFragment = AccountFragment()
        fragmentList.add(homeFragment)
        fragmentList.add(movieFragment)
        fragmentList.add(accountFragment)
        main_viewpager.offscreenPageLimit = 3
        main_viewpager.adapter = KotlinPagerAdapter(fragmentList,supportFragmentManager)
    }



    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            if (isTaskRoot) {
                moveTaskToBack(false)
                return true
            } else {
                return super.onKeyDown(keyCode, event)

            }
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->{
                draw_layout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}


class KotlinPagerAdapter(var mList : List<Fragment>, fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): android.support.v4.app.Fragment {
        return mList[position]
    }

    override fun getCount(): Int {
        return mList.size
    }

}