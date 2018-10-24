package com.ksballetba.timemovie.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.model.bean.CinemaDetailBean

class CinemaDetailTimeAdapter(val mItems:MutableList<CinemaDetailBean.Showtime>, internal val didSelectedAtPos:(idx:Int)->Unit): RecyclerView.Adapter<CinemaDetailTimeAdapter.ViewHolder>() {
    internal var mContext: Context? = null
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var startTime = view.findViewById<TextView>(R.id.showtime_item_starttime)
        var endTime = view.findViewById<TextView>(R.id.showtime_item_endtime)
        var type = view.findViewById<TextView>(R.id.showtime_item_type)
        var hall = view.findViewById<TextView>(R.id.showtime_item_hall)
        var ticketPrice = view.findViewById<TextView>(R.id.showtime_item_price)
        var ticketDiscount = view.findViewById<TextView>(R.id.showtime_item_discount)
        var bookBtn = view.findViewById<Button>(R.id.booking_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        if(mContext==null){
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_showtime_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        fun bind(model:CinemaDetailBean.Showtime){
            holder.startTime.text = model.realtime.substring(model.realtime.length-8,model.realtime.length-3)
            holder.endTime.text = if(model.movieEndTime.isNotEmpty()) model.movieEndTime.substring(2,model.movieEndTime.length) else ""
            holder.type.text = model.language+model.version
            holder.hall.text = model.hallName
            holder.ticketPrice.text = model.mtimePrice.toString()+"元"
            holder.ticketDiscount.text = "折扣卡${model.mtimePrice-8}元"
            with(holder.bookBtn){
                setOnClickListener {
                    didSelectedAtPos(position)
                }
            }
        }
        val item = mItems[position]
        bind(item)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun update(newData:MutableList<CinemaDetailBean.Showtime>){
        mItems.clear()
        mItems.addAll(newData)
        notifyDataSetChanged()
    }
}