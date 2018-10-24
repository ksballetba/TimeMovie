package com.ksballetba.timemovie.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.model.bean.OrderBean
import com.ksballetba.timemovie.ui.widgets.SeatTable
import com.ksballetba.timemovie.utils.getCurrentDate
import io.github.tonnyl.light.Light.success
import io.github.tonnyl.light.Light.error
import kotlinx.android.synthetic.main.activity_seat.*
import kotlinx.android.synthetic.main.fragment_movie.*

class SeatActivity : AppCompatActivity() {

    var mTicketPrice: Int = 0
    var mSelectedSeatNum: Int = 0
    var mCinemaName: String = ""
    var mIsFinished: Boolean = false
    var mMoviePoster: String= ""
    var mMovieName: String= ""
    var mTicketNum: Int = 0
    var mMovieShowtime: String= ""
    var mHallName: String= ""
    var mHallLocation: String= ""
    var mTotolPrice: Int = 0
    var mIsSure = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat)
        val movieName = intent.getStringExtra("movie_name")
        val cinemaName = intent.getStringExtra("cinema_name")
        val showDate = intent.getStringExtra("show_date")
        val showTime = intent.getStringExtra("show_time")
        val showHall = intent.getStringExtra("show_hall")
        val showType = intent.getStringExtra("show_type")
        val ticketPrice = intent.getIntExtra("ticket_price", 0)
        mCinemaName = cinemaName
        mMoviePoster = intent.getStringExtra("movie_poster")
        mMovieName = movieName
        mMovieShowtime = "$showDate $showTime"
        mHallName = showHall
        mTicketPrice = ticketPrice
        initToolbar(movieName, cinemaName, showDate, showHall, showTime, showType, ticketPrice)
        initSeatView(showHall)
    }

    private fun initToolbar(movieName: String, cinemaName: String, showDate: String, showHall: String, showTime: String, showType: String, ticketPrice: Int) {
        setSupportActionBar(seat_toolbar)
        supportActionBar?.title = ""
        seat_movie_title.text = movieName
        seat_cinema_name.text = cinemaName
        seat_showdate.text = showDate
        seat_showtime.text = showTime
        seat_movie_hall.text = showHall + " " + showType
        seat_totol_price.text = "总额：￥0"
        seat_pay_btn.setOnClickListener {
            saveOrders(mCinemaName, false, mMoviePoster, mMovieName, mTicketNum, mMovieShowtime, mHallName, mHallLocation, mTotolPrice)
        }
    }

    private fun initSeatView(showHall: String) {
        val hallLocationArr = mutableListOf<String>()
        seat_view.setScreenName(showHall)
        seat_view.setMaxSelected(3)
        seat_view.setSeatChecker(object : SeatTable.SeatChecker {
            override fun isValidSeat(row: Int, column: Int): Boolean {
                if (column == 2) {
                    return false
                }
                return true
            }

            override fun isSold(row: Int, column: Int): Boolean {
                if (row == 6 && column == 5) {
                    return true
                }
                return false
            }

            override fun checked(row: Int, column: Int) {
                mSelectedSeatNum++
                seat_totol_price.text = "总额：￥${mTicketPrice * mSelectedSeatNum}"
                mTicketNum = mSelectedSeatNum
                mTotolPrice = mTicketPrice * mSelectedSeatNum
                hallLocationArr.add(row.toString() + "排" + column.toString() + "座")
                val sb = StringBuffer()
                for (s in hallLocationArr) {
                    sb.append(s + " ")
                }
                mHallLocation = sb.toString()
            }

            override fun unCheck(row: Int, column: Int) {
                mSelectedSeatNum--
                seat_totol_price.text = "总额：￥${mTicketPrice * mSelectedSeatNum}"
                mTicketNum = mSelectedSeatNum
                mTotolPrice = mTicketPrice * mSelectedSeatNum
                hallLocationArr.removeAll {
                    it == row.toString() + "排" + column.toString() + "座"
                }
                val sb = StringBuffer()
                for (s in hallLocationArr) {
                    sb.append(s)
                }
                mHallLocation = sb.toString()
            }

            override fun checkedSeatTxt(row: Int, column: Int): Array<String> {
                return arrayOf()
            }
        })
        seat_view.setData(10, 12)
    }

    private fun saveOrders(cinemaName: String,
                           isFinished: Boolean,
                           moviePoster: String,
                           movieName: String,
                           ticketNum: Int,
                           movieShowtime: String,
                           hallName: String,
                           hallLocation: String,
                           totolPrice: Int) {
        if(!mIsSure&&mHallLocation.isNotEmpty()){
            val orders = OrderBean(cinemaName, isFinished, moviePoster, movieName, ticketNum, movieShowtime, hallName, hallLocation, totolPrice)
            orders.save()
            success(seat_pay_btn, "预定成功，前往订单查看", Snackbar.LENGTH_LONG)
                    .setAction("前往", {
                        val intent = Intent(this,OrdersActivity::class.java)
                        startActivity(intent)
                    })
                    .show()
            mIsSure = true
        } else{
            error(seat_pay_btn, "请选择座位", Snackbar.LENGTH_SHORT).show()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
