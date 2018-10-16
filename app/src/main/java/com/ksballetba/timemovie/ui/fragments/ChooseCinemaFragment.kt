package com.ksballetba.timemovie.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.contract.ChooseCinemaContract
import com.ksballetba.timemovie.mvp.model.bean.CinemaBean
import com.ksballetba.timemovie.mvp.presenter.ChooseCinemaPresenter
import com.ksballetba.timemovie.ui.activities.ChooseCinemaActivity
import com.ksballetba.timemovie.ui.activities.CinemaDetailActivity
import com.ksballetba.timemovie.ui.adapters.ChooseCinemaAdapter
import com.ksballetba.timemovie.utils.getCurrentDate
import com.ksballetba.timemovie.utils.parseStringToDate
import kotlinx.android.synthetic.main.fragment_choose_cinema.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ChooseCinemaFragment : Fragment(),ChooseCinemaContract.View {

    lateinit var mPresenter:ChooseCinemaPresenter
    lateinit var mAdapter: ChooseCinemaAdapter
    var mCinemaList = mutableListOf<CinemaBean>()
    var mDate:String = parseStringToDate(getCurrentDate())


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_cinema, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        val cinemaLayoutManager = LinearLayoutManager(context)
        cinemaLayoutManager.orientation = LinearLayoutCompat.VERTICAL
        choose_cinema_rec.layoutManager = cinemaLayoutManager

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val movieId = sharedPreferences.getString("movie_id"," ")
        val location = sharedPreferences.getString("location", " ")
        mAdapter = ChooseCinemaAdapter(mCinemaList){idx ->
            jumpToCinemaDetail(mCinemaList[idx].cname,mCinemaList[idx].showtimepage,movieId,mDate)
        }
        val act = activity as ChooseCinemaActivity
        act.requestData(location,movieId)
        choose_cinema_rec.adapter = mAdapter
    }

    fun requestData(location:String, movieId:String, date:String){
        mPresenter = ChooseCinemaPresenter(context,this)
        mPresenter.requestData(location,movieId,date)
    }

    override fun setData(cinemaList: MutableList<CinemaBean>) {
        activity?.runOnUiThread {
            mCinemaList = cinemaList
            mAdapter.update(mCinemaList)
            activity?.findViewById<SwipeRefreshLayout>(R.id.choose_cinema_refresh)?.isRefreshing = false
        }
    }

    private fun jumpToCinemaDetail(cinemaName:String,cinemaUrl:String,curMovieId:String,curDate:String){
        val intent = Intent(activity, CinemaDetailActivity::class.java)
        intent.putExtra("cinema_name",cinemaName)
        intent.putExtra("cinema_url",cinemaUrl)
        intent.putExtra("cinema_chosed_movieid",curMovieId)
        intent.putExtra("cinema_chosed_date",curDate)
        startActivity(intent)
    }

}
