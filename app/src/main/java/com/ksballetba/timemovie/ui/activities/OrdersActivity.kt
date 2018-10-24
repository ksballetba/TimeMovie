package com.ksballetba.timemovie.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.contract.OrderContract
import com.ksballetba.timemovie.mvp.model.bean.OrderBean
import com.ksballetba.timemovie.mvp.presenter.OrderPresenter
import com.ksballetba.timemovie.ui.adapters.OrdersAdapter
import kotlinx.android.synthetic.main.activity_orders.*

class OrdersActivity : AppCompatActivity(),OrderContract.View {

    lateinit var mPresenter:OrderPresenter
    lateinit var mAdapter:OrdersAdapter
    var mList = mutableListOf<OrderBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        initToolbar()
        initRec()
    }

    private fun initToolbar(){
        setSupportActionBar(order_toolbar)
        supportActionBar?.title = ""
        order_title.text = "我的订单"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRec(){
        mAdapter = OrdersAdapter(mList)
        val mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutCompat.VERTICAL
        order_rec.layoutManager = mLayoutManager
        order_rec.adapter = mAdapter
        mPresenter = OrderPresenter(this,this)
        mPresenter.requestData()
    }

    override fun setData(orderList: MutableList<OrderBean>) {
        mList = orderList
        mAdapter.update(mList)
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
