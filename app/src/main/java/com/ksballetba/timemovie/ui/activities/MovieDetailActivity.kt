
package com.ksballetba.timemovie.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.contract.MovieCommentsContract
import com.ksballetba.timemovie.mvp.contract.MovieDetailContract
import com.ksballetba.timemovie.mvp.model.bean.MovieCommentsBean
import com.ksballetba.timemovie.mvp.model.bean.MovieDetailBean
import com.ksballetba.timemovie.mvp.presenter.MovieCommentsPresenter
import com.ksballetba.timemovie.mvp.presenter.MovieDetailPresenter
import com.ksballetba.timemovie.ui.adapters.ActorsAdapter
import com.ksballetba.timemovie.ui.adapters.MovieCommentsAdapter
import com.ksballetba.timemovie.ui.adapters.StagePicAdapter
import com.ksballetba.timemovie.ui.widgets.AppBarStateChangeListener
import com.ksballetba.timemovie.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.concurrent.thread

class MovieDetailActivity : AppCompatActivity(),MovieDetailContract.View,MovieCommentsContract.View {
    
    lateinit var mDetailPresenter:MovieDetailPresenter
    lateinit var mCommentsPresenter:MovieCommentsPresenter
    var mActorList= mutableListOf<MovieDetailBean.Data.Basic.Actor>()
    var mStagePicList=mutableListOf<MovieDetailBean.Data.Basic.StageImg.X>()
    var mCommentsList = mutableListOf<MovieCommentsBean.Data.Ct>()
    lateinit var mActorAdapter:ActorsAdapter
    lateinit var mStagePicAdapter: StagePicAdapter
    lateinit var mCommentsAdapter: MovieCommentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        init()
    }

    private fun init(){
        setSupportActionBar(movie_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        val locationId = intent.getStringExtra("location_id")
        val movieId = intent.getStringExtra("movie_id")
        mDetailPresenter = MovieDetailPresenter(this,this)
        movie_detail_refresh.isRefreshing = true
        movie_detail.visibility = View.INVISIBLE
        mDetailPresenter.requestData(locationId,movieId)
        movie_wanted.text = intent.getStringExtra("wanted_count")
        val actorsLayoutManager = LinearLayoutManager(this)
        actorsLayoutManager.orientation = LinearLayoutCompat.HORIZONTAL
        movie_actors_rec.layoutManager = actorsLayoutManager
        mActorAdapter = ActorsAdapter(mActorList)
        movie_actors_rec.adapter = mActorAdapter
        val picLayoutManager = LinearLayoutManager(this)
        picLayoutManager.orientation = LinearLayoutCompat.HORIZONTAL
        movie_image_rec.layoutManager = picLayoutManager
        mStagePicAdapter = StagePicAdapter(mStagePicList)
        movie_image_rec.adapter = mStagePicAdapter

        mCommentsPresenter = MovieCommentsPresenter(this,this)
        mCommentsPresenter.requestData(movieId)
        val commentsLayoutManager = LinearLayoutManager(this)
        commentsLayoutManager.orientation = LinearLayoutCompat.VERTICAL
        movie_comments_rec.layoutManager = commentsLayoutManager
        mCommentsAdapter = MovieCommentsAdapter(mCommentsList)
        movie_comments_rec.adapter = mCommentsAdapter
        movie_appbarlayout.addOnOffsetChangedListener(AppBarStateChangeListener{appBarLayout, state ->
            when(state){
                AppBarStateChangeListener.State.COLLAPSED->{
                    movie_coll_title.visibility = View.VISIBLE
                    movie_detail_refresh.isEnabled = false
                }
                AppBarStateChangeListener.State.EXPANDED->{
                    actionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
                    movie_coll_title.visibility = View.GONE
                    movie_detail_refresh.isEnabled = true
                }
                AppBarStateChangeListener.State.IDLE->{
                    movie_detail_refresh.isEnabled = false
                }
            }
        })
        movie_detail_refresh.setOnRefreshListener {
            mDetailPresenter.requestData(locationId,movieId)
        }

    }

    override fun setData(bean: MovieDetailBean) {
        doAsync {
            thread {
                val blur = ImageUtils(this@MovieDetailActivity)
                val bgBitmap = Glide.with(applicationContext).asBitmap().load(bean.data.basic.img).submit(720,500).get()
                uiThread {
                    Glide.with(this@MovieDetailActivity).load(bean.data.basic.img).into(movie_poster)
                    Glide.with(this@MovieDetailActivity).load(blur.gaussianBlur(25,bgBitmap)).into(movie_bg)
                    movie_title.text = bean.data.basic.name
                    movie_eng_title.text = bean.data.basic.nameEn
                    movie_coll_title.text = bean.data.basic.name
                    val sb = StringBuffer()
                    for(s in bean.data.basic.type){
                        sb.append("$s ")
                    }
                    movie_type.text = sb.toString()
                    movie_release_country.text = bean.data.basic.releaseArea+"/"+bean.data.basic.mins
                    movie_release_time.text = bean.data.basic.releaseDate.substring(0,4)+"-"+bean.data.basic.releaseDate.substring(4,6)+"-"+bean.data.basic.releaseDate.substring(6,8)+"上映"
                    movie_expand_sum.text = bean.data.basic.story
                    mActorList = bean.data.basic.actors.toMutableList()
                    mActorAdapter.update(mActorList)
                    mStagePicList = bean.data.basic.stageImg.list.toMutableList()
                    mStagePicAdapter.update(mStagePicList)
                    movie_detail_refresh.isRefreshing = false
                    movie_detail.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun setData(bean: MovieCommentsBean) {
        mCommentsList = bean.data.cts.toMutableList()
        mCommentsAdapter.update(mCommentsList)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->{
                finishAfterTransition()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
