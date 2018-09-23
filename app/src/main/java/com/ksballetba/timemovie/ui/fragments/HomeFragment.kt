package com.ksballetba.timemovie.ui.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.R.id.banner
import com.ksballetba.timemovie.mvp.contract.BookingMovieContract
import com.ksballetba.timemovie.mvp.contract.ComingMovieContract
import com.ksballetba.timemovie.mvp.contract.ShowingMovieContract
import com.ksballetba.timemovie.mvp.model.bean.BookingMovieBean
import com.ksballetba.timemovie.mvp.model.bean.ComingMovieBean
import com.ksballetba.timemovie.mvp.model.bean.ShowingMovieBean
import com.ksballetba.timemovie.mvp.presenter.BookingMoviePresenter
import com.ksballetba.timemovie.mvp.presenter.ComingMoviePresenter
import com.ksballetba.timemovie.mvp.presenter.ShowingMoviePresenter
import com.ksballetba.timemovie.ui.activities.MovieDetailActivity
import com.ksballetba.timemovie.ui.adapters.HomeBookingMovieAdapter
import com.ksballetba.timemovie.ui.adapters.HomeComingMovieAdapter
import com.ksballetba.timemovie.ui.adapters.HomeShowingMovieAdapter
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.toast
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment(),ShowingMovieContract.View,BookingMovieContract.View,ComingMovieContract.View {

    lateinit var mBookingMoviePresenter:BookingMoviePresenter
    lateinit var mShowingMoviePresenter:ShowingMoviePresenter
    lateinit var mComingMoviePresenter:ComingMoviePresenter
    var mBookingMovieList = mutableListOf<BookingMovieBean.Movy>()
    var mShowingMovieList = mutableListOf<ShowingMovieBean.M>()
    var mComingMovieList = mutableListOf<ComingMovieBean.Moviecoming>()
    lateinit var mShowingMovieAdapter:HomeShowingMovieAdapter
    lateinit var mBookingMovieAdapter:HomeBookingMovieAdapter
    lateinit var mComingMovieAdapter:HomeComingMovieAdapter

    var locationId = "561"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun setData(bean: ShowingMovieBean) {
        mShowingMovieList = bean.ms.toMutableList()
        mShowingMovieAdapter.update(mShowingMovieList.subList(16,30))
        all_showing_movie.text = "全部${bean.ms.size}部〉"
        Log.d("debug",bean.ms[0].actors)
    }

    override fun setData(bean: BookingMovieBean) {
        mBookingMovieList = bean.movies.toMutableList()
        mBookingMovieAdapter.update(mBookingMovieList.subList(0,15))
        all_booking_movie.text = "全部${bean.movies.size}部〉"
    }

    override fun setData(bean: ComingMovieBean) {
        mComingMovieList = bean.moviecomings.toMutableList()
        mComingMovieAdapter.update(mComingMovieList.subList(0,15))
        all_coming_movie.text = "全部${bean.moviecomings.size}部〉"
        val bannerList = ArrayList<String>()
        for(i in 0 until 5){
            bannerList.add(bean.moviecomings[i].videos[0].image)
        }
        banner.setImages(bannerList).setImageLoader(GlideImageLoader()).start()
        home_refresh.isRefreshing = false
    }

    private fun init(){
        home_refresh.isRefreshing = true
        mShowingMoviePresenter = ShowingMoviePresenter(context,this)
        mBookingMoviePresenter = BookingMoviePresenter(context,this)
        mComingMoviePresenter = ComingMoviePresenter(context,this)
        mShowingMoviePresenter.requestData("561")
        mBookingMoviePresenter.requestData("561")
        mComingMoviePresenter.requestData("561")
        val showingLayoutManager = LinearLayoutManager(context)
        showingLayoutManager.orientation = LinearLayoutCompat.HORIZONTAL
        home_showing_movie_rec.layoutManager = showingLayoutManager
        mShowingMovieAdapter = HomeShowingMovieAdapter(mShowingMovieList){idx->
            jumpToMovieDetail(locationId,mShowingMovieList[idx+16].id.toString(),mShowingMovieList[idx].wantedCount.toString())
        }
        home_showing_movie_rec.adapter = mShowingMovieAdapter

        val bookingLayoutManager = LinearLayoutManager(context)
        bookingLayoutManager.orientation = LinearLayoutCompat.HORIZONTAL
        home_booking_movie_rec.layoutManager = bookingLayoutManager
        mBookingMovieAdapter = HomeBookingMovieAdapter(mBookingMovieList){idx->
            jumpToMovieDetail(locationId,mBookingMovieList[idx].movieId.toString(),mBookingMovieList[idx].wantedCount.toString())
        }
        home_booking_movie_rec.adapter = mBookingMovieAdapter

        val comingLayoutManager = LinearLayoutManager(context)
        comingLayoutManager.orientation = LinearLayoutCompat.HORIZONTAL
        home_coming_movie_rec.layoutManager = comingLayoutManager
        mComingMovieAdapter = HomeComingMovieAdapter(mComingMovieList){idx->
            jumpToMovieDetail(locationId,mComingMovieList[idx].id.toString(),mComingMovieList[idx].wantedCount.toString())
        }
        home_coming_movie_rec.adapter = mComingMovieAdapter

        home_refresh.setOnRefreshListener {
            mShowingMoviePresenter.requestData(locationId)
            mBookingMoviePresenter.requestData(locationId)
            mComingMoviePresenter.requestData(locationId)
        }
    }

    private fun jumpToMovieDetail(locationId:String,movieId:String,wantedCount:String){
        val intent = Intent(activity,MovieDetailActivity::class.java)
        intent.putExtra("location_id",locationId)
        intent.putExtra("movie_id",movieId)
        intent.putExtra("wanted_count",wantedCount)
        startActivity(intent)
    }
}

class GlideImageLoader: ImageLoader(){
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        Glide.with(context!!).load(path).into(imageView!!)
    }
}

