package com.ksballetba.timemovie.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.util.Log
import android.view.MenuItem
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.ui.fragments.ChooseCinemaFragment
import com.ksballetba.timemovie.utils.getAfterDate
import com.ksballetba.timemovie.utils.getCurrentDate
import com.ksballetba.timemovie.utils.parseStringToDate
import kotlinx.android.synthetic.main.activity_choose_cinema.*
import org.jetbrains.anko.doAsync

class ChooseCinemaActivity : AppCompatActivity() {

    lateinit var chooseCinemaFragment1:ChooseCinemaFragment
    lateinit var chooseCinemaFragment2:ChooseCinemaFragment
    lateinit var chooseCinemaFragment3:ChooseCinemaFragment
    lateinit var chooseCinemaFragment4:ChooseCinemaFragment
    lateinit var chooseCinemaFragment5:ChooseCinemaFragment
    val fragmentList = ArrayList<ChooseCinemaFragment>()

    var day1:String = getCurrentDate()
    var day2:String = getAfterDate(getCurrentDate(),1)
    var day3:String = getAfterDate(getCurrentDate(),2)
    var day4:String = getAfterDate(getCurrentDate(),3)
    var day5:String = getAfterDate(getCurrentDate(),4)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_cinema)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
//        val movieId = intent.getStringExtra("movie_chosed_id")
        val movieId = sharedPreferences.getString("movie_id"," ")
        val location = sharedPreferences.getString("location", " ")
        val movieTitle = intent.getStringExtra("movie_chosed_title")
        choose_cinema_title.text = movieTitle
        setSupportActionBar(choose_cinema_toolbar)
        supportActionBar?.title = ""
        initFragments()
        requestData(location,movieId)
        choose_cinema_refresh.setOnRefreshListener {
            requestData(location,movieId)
        }
    }

    private fun initFragments(){
        chooseCinemaFragment1 = ChooseCinemaFragment()
        chooseCinemaFragment2 = ChooseCinemaFragment()
        chooseCinemaFragment3 = ChooseCinemaFragment()
        chooseCinemaFragment4 = ChooseCinemaFragment()
        chooseCinemaFragment5 = ChooseCinemaFragment()
        fragmentList.add(chooseCinemaFragment1)
        fragmentList.add(chooseCinemaFragment2)
        fragmentList.add(chooseCinemaFragment3)
        fragmentList.add(chooseCinemaFragment4)
        fragmentList.add(chooseCinemaFragment5)
        choose_cinema_viewpager.adapter = KotlinPagerAdapter(fragmentList,supportFragmentManager)
        chooseCinemaFragment1.mDate = parseStringToDate(day1)
        chooseCinemaFragment2.mDate = parseStringToDate(day2)
        chooseCinemaFragment3.mDate = parseStringToDate(day3)
        chooseCinemaFragment4.mDate = parseStringToDate(day4)
        chooseCinemaFragment5.mDate = parseStringToDate(day5)
        choose_cinema_tablayout.setupWithViewPager(choose_cinema_viewpager)
        choose_cinema_tablayout.getTabAt(0)?.text = parseStringToDate(day1)
        choose_cinema_tablayout.getTabAt(1)?.text = parseStringToDate(day2)
        choose_cinema_tablayout.getTabAt(2)?.text = parseStringToDate(day3)
        choose_cinema_tablayout.getTabAt(3)?.text = parseStringToDate(day4)
        choose_cinema_tablayout.getTabAt(4)?.text = parseStringToDate(day5)
    }

    fun requestData(location:String,movieId:String){
        choose_cinema_refresh.isRefreshing = true
        doAsync {
            chooseCinemaFragment1.requestData(location,movieId,day1)
            chooseCinemaFragment2.requestData(location,movieId,day2)
            chooseCinemaFragment3.requestData(location,movieId,day3)
            chooseCinemaFragment4.requestData(location,movieId,day4)
            chooseCinemaFragment5.requestData(location,movieId,day5)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
