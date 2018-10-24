package com.ksballetba.timemovie.ui.activities

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.transition.Slide
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.bumptech.glide.Glide
import com.github.promeg.pinyinhelper.Pinyin
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.ui.activities.LoginActivity.Companion.APP_ID
import com.ksballetba.timemovie.ui.fragments.CinemaFragment
import com.ksballetba.timemovie.ui.fragments.HomeFragment
import com.ksballetba.timemovie.ui.fragments.MovieFragment
import com.ksballetba.timemovie.utils.*
import com.tencent.connect.UserInfo
import com.tencent.connect.common.Constants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cinema.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.share
import org.jetbrains.anko.toast
import org.json.JSONObject
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
    lateinit var cinemaFragment: CinemaFragment
    lateinit var mLocationClient: AMapLocationClient
    lateinit var mLocationOption: AMapLocationClientOption
    lateinit var mTencent: Tencent


    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        super.onCreate(savedInstanceState)
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        setContentView(R.layout.activity_main)
        mTencent = Tencent.createInstance(APP_ID,applicationContext)
        initToolbar()
        initFragments()
        initAmap()
    }

    override fun onStart() {
        showQQUserInfo()
        super.onStart()
    }

    fun initAmap() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sharedPreferences.edit()
        mLocationClient = AMapLocationClient(applicationContext)
        val mLocationListener = AMapLocationListener { aMapLocation ->
            if (aMapLocation != null) {
                if (aMapLocation.errorCode == 0) {
                    val location = locationParse(aMapLocation.province,aMapLocation.city)
                    editor.putString("location",location)
                    editor.apply()
                    doAsync {
                        cinemaFragment.requestData(location)
                    }
                } else {
                    Log.d("debug", aMapLocation.errorInfo)
                }
                mLocationClient.stopLocation()
            }
        }
        mLocationClient.setLocationListener(mLocationListener)
        requestPermissions()
    }

    private fun initToolbar() {
        setSupportActionBar(main_toolbar)
        supportActionBar?.title = "首页"
        main_bnv.setOnNavigationItemSelectedListener { MenuItem ->
            when (MenuItem.itemId) {
                R.id.menu_home -> {
//                    MenuItem.isChecked = true
                    supportActionBar?.title = "首页"
                    main_viewpager.currentItem = 0
                }
                R.id.menu_movie -> {
//                    MenuItem.isChecked = true
                    supportActionBar?.title = "电影"
                    main_viewpager.currentItem = 1
                }
                R.id.menu_account -> {
//                    MenuItem.isChecked = true
                    supportActionBar?.title = "影院"
                    main_viewpager.currentItem = 2
                }
            }
            true
        }
        nav_view.getHeaderView(0).setOnClickListener {
            if(!mTencent.checkSessionValid(APP_ID)){
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            } else {
                toast("切换账号请先退出登录")
            }
        }
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_order->{
                    val intent = Intent(this,OrdersActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_exit -> {
                    mTencent.logout(applicationContext)
                    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                    val editor = sharedPreferences.edit()
                    editor.putString("qq_user_avater","meide")
                    editor.putString("qq_user_nickname","未登录")
                    editor.apply()
                    val navAvater = nav_view.getHeaderView(0).findViewById<ImageView>(R.id.nav_headavatar)
                    val navNickname = nav_view.getHeaderView(0).findViewById<TextView>(R.id.nav_headnickname)
                    Glide.with(this).load(R.drawable.avatar).into(navAvater)
                    navNickname.text = "未登录"
                }
            }
            false
        }
    }

    private fun showQQUserInfo(){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val qqUserAvater = sharedPreferences.getString("qq_user_avater","meide")
        val qqUserNickname = sharedPreferences.getString("qq_user_nickname","未登录")
        val navAvater = nav_view.getHeaderView(0).findViewById<CircleImageView>(R.id.nav_headavatar)
        val navNickname = nav_view.getHeaderView(0).findViewById<TextView>(R.id.nav_headnickname)
        if(qqUserAvater!="meide"){
            Glide.with(this).load(qqUserAvater).into(navAvater)
            navNickname.text = qqUserNickname
        }
    }

    private fun initFragments() {
        homeFragment = HomeFragment()
        movieFragment = MovieFragment()
        cinemaFragment = CinemaFragment()
        fragmentList.add(homeFragment)
        fragmentList.add(movieFragment)
        fragmentList.add(cinemaFragment)
        main_viewpager.offscreenPageLimit = 3
        main_viewpager.adapter = KotlinPagerAdapter(fragmentList, supportFragmentManager)
    }

    private fun initLocation() {
        mLocationOption = AMapLocationClientOption()
        mLocationOption.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
        mLocationOption.isOnceLocation = true
        mLocationOption.isOnceLocationLatest = true
        mLocationOption.isNeedAddress = true
        mLocationOption.httpTimeOut = 20000
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption)
            mLocationClient.stopLocation()
            mLocationClient.startLocation()
        }
    }

    fun locationParse(locationProvince:String,locationCity: String): String {
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(this)))
        val province = locationProvince.substring(0,locationProvince.length-1)
        val city = locationCity.substring(0,locationCity.length-1)
        val country = "China_"
        val PROVINCE = "Province_"
        when(city){
            "上海","天津","北京","重庆"->{
                val result = Pinyin.toPinyin(city,"")
                return country+result.first()+result.substring(1,result.length).toLowerCase()
            }
            else->{
                val resultP = Pinyin.toPinyin(province,"")
                val resultC = Pinyin.toPinyin(city,"")
                return country+resultP.first()+resultP.substring(1,resultP.length).toLowerCase()+"_"+PROVINCE+
                        resultC.first()+resultC.substring(1,resultC.length).toLowerCase()
            }
        }
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
        when (item?.itemId) {
            android.R.id.home -> {
                draw_layout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(MainActivity.PERMISSISON_CODE)
    private fun requestPermissions() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)) {
            initLocation()
        } else {
            EasyPermissions.requestPermissions(this, "需要获取权限",
                    MainActivity.PERMISSISON_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}


class KotlinPagerAdapter(var mList: List<Fragment>, fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): android.support.v4.app.Fragment {
        return mList[position]
    }

    override fun getCount(): Int {
        return mList.size
    }

}