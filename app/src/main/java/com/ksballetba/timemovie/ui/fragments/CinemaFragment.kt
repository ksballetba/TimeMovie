package com.ksballetba.timemovie.ui.fragments


import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.InputFilter
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
import com.github.promeg.pinyinhelper.Pinyin
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict

import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.contract.CinemaContract
import com.ksballetba.timemovie.mvp.model.bean.CinemaBean
import com.ksballetba.timemovie.mvp.presenter.CinemaPresenter
import com.ksballetba.timemovie.ui.activities.ChooseCinemaActivity
import com.ksballetba.timemovie.ui.activities.CinemaDetailActivity
import com.ksballetba.timemovie.ui.activities.MainActivity
import com.ksballetba.timemovie.ui.adapters.ChooseCinemaAdapter
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
class CinemaFragment : Fragment(),CinemaContract.View {

    var mCinemaList = mutableListOf<CinemaBean>()
    lateinit var mAdapter:ChooseCinemaAdapter
    lateinit var mPresenter:CinemaPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cinema, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    private fun init(){
        val cinemaLayoutManager = LinearLayoutManager(context)
        cinemaLayoutManager.orientation = LinearLayoutCompat.VERTICAL
        cinema_rec.layoutManager = cinemaLayoutManager
        mAdapter = ChooseCinemaAdapter(mCinemaList){idx->
            jumpToCinemaDetail(mCinemaList[idx].cname,mCinemaList[idx].showtimepage)
        }
        cinema_rec.adapter = mAdapter
        cinema_refresh.isRefreshing = true
        cinema_refresh.setOnRefreshListener {
            val act = activity as MainActivity
            act.initAmap()
        }
    }

    fun requestData(location:String){
        mPresenter = CinemaPresenter(context,this)
        mPresenter.requestData(location)
    }

    override fun setData(cinemaList: MutableList<CinemaBean>) {
        activity?.runOnUiThread {
            mCinemaList = cinemaList.subList(0,cinemaList.size/2)
            mAdapter.update(mCinemaList)
            cinema_refresh.isRefreshing = false
        }
    }


    private fun jumpToCinemaDetail(cinemaName:String,cinemaUrl:String){
        val intent = Intent(activity, CinemaDetailActivity::class.java)
        intent.putExtra("cinema_name",cinemaName)
        intent.putExtra("cinema_url",cinemaUrl)
        startActivity(intent)
    }
}
