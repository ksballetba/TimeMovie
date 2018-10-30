package com.ksballetba.timemovie.ui.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.*
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.TabHost
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.contract.CinemaDetailContract
import com.ksballetba.timemovie.mvp.model.bean.CinemaDetailBean
import com.ksballetba.timemovie.mvp.presenter.CinemaDetailPresenter
import com.ksballetba.timemovie.ui.adapters.CinemaDetailMovieAdapter
import com.ksballetba.timemovie.ui.adapters.CinemaDetailTimeAdapter
import com.ksballetba.timemovie.ui.widgets.GalleryItemDecoration
import com.ksballetba.timemovie.ui.widgets.GalleryScrollManager
import com.ksballetba.timemovie.utils.ImageUtils
import com.ksballetba.timemovie.utils.parseStringToDate
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.activity_cinema_detail.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import kotlin.concurrent.thread
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.Fade
import android.transition.TransitionSet
import com.ksballetba.timemovie.ui.widgets.CenterLayoutManager
import com.ksballetba.timemovie.utils.isActivityDestroyed
import kotlinx.android.synthetic.main.activity_login.*


class CinemaDetailActivity : AppCompatActivity(), CinemaDetailContract.View {

    lateinit var mMovieAdapter: CinemaDetailMovieAdapter
    lateinit var mTimeAdapter: CinemaDetailTimeAdapter
    lateinit var mPresenter: CinemaDetailPresenter
    var mMovieList = mutableListOf<CinemaDetailBean.Movy>()
    var mTimeList = mutableListOf<CinemaDetailBean.Showtime>()
    var mValueDateList = mutableListOf<CinemaDetailBean.ValueDate>()
    var tempTimeList = mutableListOf<CinemaDetailBean.Showtime>()
    lateinit var mScrollManager: GalleryScrollManager
    val bgCacheMap = HashMap<String, Drawable>()
    var mPosition = 0
    var mTabSelctecPos = 0
    var mSelectedMovieName: String? = null
    var mCinemaName: String? = null
    var isFirstIn = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cinema_detail)
        initToolbar()
        initRec()
        initPresenter()
    }

    private fun initToolbar() {
        setSupportActionBar(cinema_detial_toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val cinemaName = intent.getStringExtra("cinema_name")
        mCinemaName = cinemaName
        cinema_detial_title.text = cinemaName
    }

    private fun initPresenter() {
        val cinemaUrl = intent.getStringExtra("cinema_url")
        mPresenter = CinemaDetailPresenter(this, this)
        cinema_detail_refresh.isRefreshing = true
        cinema_detail.visibility = View.INVISIBLE
        cinema_detail_refresh.setOnRefreshListener {
            doAsync {
                mPresenter.requestData(cinemaUrl)
            }
        }
        doAsync {
            mPresenter.requestData(cinemaUrl)
        }
    }

    private fun initRec() {
        val movieLayoutManager = CenterLayoutManager(this)
        movieLayoutManager.orientation = LinearLayoutCompat.HORIZONTAL
        cinema_detail_movierec.layoutManager = movieLayoutManager
        mMovieAdapter = CinemaDetailMovieAdapter(mMovieList) { idx ->
            cinema_detail_movierec.smoothScrollToPosition(idx)
        }
        val movieScaleAdapter = ScaleInAnimationAdapter(mMovieAdapter)
        movieScaleAdapter.setInterpolator(OvershootInterpolator())
        movieScaleAdapter.setDuration(1000)
        cinema_detail_movierec.adapter = movieScaleAdapter
        val movieSnapHelper = LinearSnapHelper()
        movieSnapHelper.attachToRecyclerView(cinema_detail_movierec)
        cinema_detail_movierec.addItemDecoration(GalleryItemDecoration(this))
        mScrollManager = GalleryScrollManager(cinema_detail_movierec) { idx ->
            setMovieRecBg(idx)
            mPosition = idx
            updateShowtime()
        }
        mScrollManager.initScroolListenner()
        val timeLayoutManager = LinearLayoutManager(this)
        timeLayoutManager.orientation = LinearLayoutCompat.VERTICAL
        cinema_detail_timerec.layoutManager = timeLayoutManager
        mTimeAdapter = CinemaDetailTimeAdapter(mTimeList) { idx ->
            val selectedTime = tempTimeList[idx]
            val selectedMovie = mMovieList[mPosition]
            jumpToSeatAct(selectedMovie.movieTitleCn, mCinemaName,
                    cinema_detail_tablayout.getTabAt(mTabSelctecPos)?.text.toString(),
                    selectedTime.hallName,
                    selectedTime.realtime.substring(selectedTime.realtime.length - 8, selectedTime.realtime.length - 3),
                    selectedTime.language, selectedTime.mtimePrice,
                    selectedMovie.coverSrc)
        }
        cinema_detail_timerec.adapter = mTimeAdapter
    }

    private fun setMovieRecBg(idx: Int) {
        cinema_detail_movietitle.text = mMovieList[idx].movieTitleCn
        cinema_detail_moviescore.text = mMovieList[idx].bigRating.toString() + "分"
        cinema_detail_movieactors.text = mMovieList[idx].runtime + "|" + mMovieList[idx].actor + "|" + mMovieList[idx].actor2
        mSelectedMovieName = mMovieList[idx].movieTitleCn
        if (isFirstIn) {
            cinema_detail_movierec.smoothScrollToPosition(idx)
        }
        doAsync {
            val imageManager = ImageUtils(this@CinemaDetailActivity)
            val bgBitmap = Glide.with(applicationContext).asBitmap().load(mMovieList[idx].coverSrc).submit(300, 520).get()
            uiThread {
                if (!isActivityDestroyed(this@CinemaDetailActivity)) {
                    Log.d("debug", bgCacheMap["PRE_BG"].toString())
                    val curBg = BitmapDrawable(resources, imageManager.gaussianBlur(25, bgBitmap))
                    val preBg = if (bgCacheMap["PRE_BG"] == null) curBg else bgCacheMap["PRE_BG"]
                    val options = RequestOptions().placeholder(preBg).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    Glide.with(this@CinemaDetailActivity).load(bgBitmap).apply(options).transition(DrawableTransitionOptions.withCrossFade(1000)).into(cinema_detail_moviebg)
                    bgCacheMap["PRE_BG"] = curBg
                }
            }
        }
    }

    private fun updateShowtime() {
        tempTimeList = mTimeList.filter {
            it.movieId == mMovieList[mPosition].movieId
        }.toMutableList()
        mTimeAdapter.update(tempTimeList)
    }

    private fun setMoviePos() {
        val curMovieId = intent.getStringExtra("cinema_chosed_movieid")
        if (curMovieId != null && isFirstIn) {
            for (i in 0 until mMovieList.size) {
                if (mMovieList[i].movieId.toString() == curMovieId) {
                    mPosition = i
                }
            }
        }
    }

    private fun setDatePos() {
        val curDate = intent.getStringExtra("cinema_chosed_date")
        if (curDate != null && isFirstIn) {
            for (i in 0 until cinema_detail_tablayout.tabCount) {
                if (cinema_detail_tablayout.getTabAt(i)?.text == curDate) {
                    mTabSelctecPos = i
                }
            }
        }
    }

    private fun jumpToSeatAct(movieName: String, cinemaName: String?, showDate: String, showHall: String, showTime: String, showType: String, ticketPrice: Int,moviePoster:String) {
        val intent = Intent(this, SeatActivity::class.java)
        intent.putExtra("movie_name", movieName)
        intent.putExtra("cinema_name", cinemaName)
        intent.putExtra("show_date", showDate)
        intent.putExtra("show_hall", showHall)
        intent.putExtra("show_time", showTime)
        intent.putExtra("show_type", showType)
        intent.putExtra("ticket_price", ticketPrice)
        intent.putExtra("movie_poster",moviePoster)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setData(bean: CinemaDetailBean?) {
        runOnUiThread {
            if (bean != null) {
                cinema_detail_name.text = bean.namecn
                cinema_detail_location.text = bean.address
                cinema_detail_tel.text = bean.telphone
                mMovieList = bean.movies.toMutableList()
                mMovieAdapter.update(mMovieList)
                setMoviePos()
                setMovieRecBg(mPosition)
                mTimeList = bean.showtimes.filter {
                    it.mtimePrice != 0
                }.toMutableList()
                updateShowtime()
                mValueDateList = bean.valueDates.toMutableList()
                cinema_detail_tablayout.removeAllTabs()
                for (i in 0 until bean.valueDates.size) {
                    val tab = cinema_detail_tablayout.newTab().setText(parseStringToDate(bean.valueDates[i].dateUrl.substring(bean.valueDates[i].dateUrl.length
                            - 8, bean.valueDates[i].dateUrl.length)))
                    cinema_detail_tablayout.addTab(tab, false)
                }
                setDatePos()
                isFirstIn = false
                cinema_detail_tablayout.getTabAt(mTabSelctecPos)?.select()
                cinema_detail_refresh.isRefreshing = false
                cinema_detail.visibility = View.VISIBLE
            }
        }
    }
}
