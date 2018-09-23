package com.ksballetba.timemovie.ui.fragments


import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.model.Marker
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch

import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.ui.activities.MainActivity
import com.ksballetba.timemovie.ui.adapters.CinemaAdapter
import kotlinx.android.synthetic.main.fragment_cinema.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_nav_head.*
import org.jetbrains.anko.toast
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment(),PoiSearch.OnPoiSearchListener {

    var mCinemaList = mutableListOf<PoiItem>()
    lateinit var mAdapter:CinemaAdapter
    lateinit var mLocationClient: AMapLocationClient
    lateinit var mLocationOption: AMapLocationClientOption

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cinema, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {

    }

    override fun onPoiSearched(result: PoiResult?, code: Int) {
        mAdapter.update(result!!.pois.toMutableList())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }

    @AfterPermissionGranted(MainActivity.PERMISSISON_CODE)
    private fun requestPermissions(){
        if (EasyPermissions.hasPermissions(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)){
            initLocation()
        } else{
            EasyPermissions.requestPermissions(this,"需要获取权限" ,
                    MainActivity.PERMISSISON_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun initRec(){
        val cinemaLayoutManager = LinearLayoutManager(context)
        cinemaLayoutManager.orientation = LinearLayoutCompat.VERTICAL
        cinema_rec.layoutManager = cinemaLayoutManager
        mAdapter = CinemaAdapter(mCinemaList){idx->
            activity?.toast(idx.toString())
        }
        cinema_rec.adapter = mAdapter
        cinema_refresh.isRefreshing = true
        cinema_refresh.setOnRefreshListener {
            initLocation()
        }
    }

    private fun init(){
        mLocationClient = AMapLocationClient(activity!!.applicationContext)
        val mLocationListener = AMapLocationListener{aMapLocation ->
            if(aMapLocation!=null){
                if(aMapLocation.errorCode ==0){
                    val query = PoiSearch.Query("电影院","",aMapLocation.district)
                    val poiSearch = PoiSearch(context,query)
                    val latLonPoint = LatLonPoint(aMapLocation.latitude,aMapLocation.longitude)
                    poiSearch.bound = PoiSearch.SearchBound(latLonPoint,5000)
                    poiSearch.setOnPoiSearchListener(this)
                    cinema_refresh.isRefreshing = false
                    poiSearch.searchPOIAsyn()
                } else{
                    Log.d("debug",aMapLocation.errorInfo)
                }
                mLocationClient.stopLocation()
            }
        }
        mLocationClient.setLocationListener(mLocationListener)
        initRec()
        requestPermissions()
    }


    private fun initLocation(){

        mLocationOption = AMapLocationClientOption()
        mLocationOption.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        mLocationOption.locationMode= AMapLocationClientOption.AMapLocationMode.Battery_Saving
        mLocationOption.isOnceLocation = true
        mLocationOption.isOnceLocationLatest = true
        mLocationOption.isNeedAddress = true
        mLocationOption.httpTimeOut = 20000
        if (null!=mLocationClient){
            mLocationClient.setLocationOption(mLocationOption)
            mLocationClient.stopLocation()
            mLocationClient.startLocation()
        }
    }


}
