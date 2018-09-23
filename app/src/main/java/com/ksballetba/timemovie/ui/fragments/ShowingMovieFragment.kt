package com.ksballetba.timemovie.ui.fragments


import android.app.ActivityOptions
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

import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.contract.ShowingMovieContract
import com.ksballetba.timemovie.mvp.model.bean.ShowingMovieBean
import com.ksballetba.timemovie.mvp.presenter.ShowingMoviePresenter
import com.ksballetba.timemovie.ui.activities.MovieDetailActivity
import com.ksballetba.timemovie.ui.adapters.HomeShowingMovieAdapter
import com.ksballetba.timemovie.ui.adapters.ShowingMovieAdapter
import kotlinx.android.synthetic.main.fragment_coming_movie.*
import kotlinx.android.synthetic.main.fragment_showing_movie.*
import org.jetbrains.anko.toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ShowingMovieFragment : Fragment(),ShowingMovieContract.View {

    lateinit var mPresenter:ShowingMoviePresenter
    var mMovieList = mutableListOf<ShowingMovieBean.M>()
    lateinit var mAdapter:ShowingMovieAdapter
    var locationId = "561"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_showing_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun setData(bean: ShowingMovieBean) {
        mMovieList = bean.ms.toMutableList()
        mAdapter.update(mMovieList)
        showing_movie_refresh.isRefreshing = false
    }

    private fun init(){
        mPresenter = ShowingMoviePresenter(context,this)
        showing_movie_refresh.isRefreshing = true
        mPresenter.requestData("561")
        val showingLayoutManager = LinearLayoutManager(context)
        showingLayoutManager.orientation = LinearLayoutCompat.VERTICAL
        showing_movie_rec.layoutManager = showingLayoutManager
        mAdapter = ShowingMovieAdapter(mMovieList){idx->
            jumpToMovieDetail(locationId,mMovieList[idx].id.toString(),mMovieList[idx].wantedCount.toString())
        }
        showing_movie_rec.adapter = mAdapter
        showing_movie_refresh.setOnRefreshListener {
            mPresenter.requestData(locationId)
        }
    }

    private fun jumpToMovieDetail(locationId:String,movieId:String,wantedCount:String){
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra("location_id",locationId)
        intent.putExtra("movie_id",movieId)
        intent.putExtra("wanted_count",wantedCount)
        startActivity(intent)
    }
}
