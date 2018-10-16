package com.ksballetba.timemovie.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.R.id.coming_movie_rec
import com.ksballetba.timemovie.mvp.contract.ComingMovieContract
import com.ksballetba.timemovie.mvp.model.bean.ComingMovieBean
import com.ksballetba.timemovie.mvp.presenter.ComingMoviePresenter
import com.ksballetba.timemovie.ui.activities.MovieDetailActivity
import com.ksballetba.timemovie.ui.adapters.ComingMovieAdapter
import kotlinx.android.synthetic.main.fragment_coming_movie.*
import org.jetbrains.anko.toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ComingMovieFragment : Fragment(),ComingMovieContract.View {

    lateinit var mPresenter:ComingMoviePresenter
    var mMovieList = mutableListOf<ComingMovieBean.Moviecoming>()
    lateinit var mAdapter:ComingMovieAdapter
    var locationId = "561"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coming_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun setData(bean: ComingMovieBean) {
        mMovieList = bean.moviecomings.toMutableList()
        mAdapter.update(mMovieList)
        coming_movie_refresh.isRefreshing = false
    }

    private fun init(){
        mPresenter = ComingMoviePresenter(context,this)
        coming_movie_refresh.isRefreshing = true
        mPresenter.requestData("561")
        val showingLayoutManager = LinearLayoutManager(context)
        showingLayoutManager.orientation = LinearLayoutCompat.VERTICAL
        coming_movie_rec.layoutManager = showingLayoutManager
        mAdapter = ComingMovieAdapter(mMovieList){idx->
            jumpToMovieDetail(locationId,mMovieList[idx].id.toString(),mMovieList[idx].wantedCount.toString(),mMovieList[idx].title)
        }
        coming_movie_rec.adapter = mAdapter
        coming_movie_refresh.setOnRefreshListener {
            mPresenter.requestData(locationId)
        }
    }

    private fun jumpToMovieDetail(locationId:String,movieId:String,wantedCount:String,movieTitle:String){
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra("location_id",locationId)
        intent.putExtra("movie_id",movieId)
        intent.putExtra("wanted_count",wantedCount)
        intent.putExtra("movie_title",movieTitle)
        startActivity(intent)
    }
}
