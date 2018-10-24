package com.ksballetba.timemovie.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.model.bean.OrderBean

class OrdersAdapter(val mItems: MutableList<OrderBean>) : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {
    internal var mContext: Context? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var orderMoviePoster = view.findViewById<ImageView>(R.id.order_item_movieposter)
        var orderMovieName = view.findViewById<TextView>(R.id.order_item_moviename)
        var orderCinemaName = view.findViewById<TextView>(R.id.order_item_cinemaname)
        var orderMovieShowtime = view.findViewById<TextView>(R.id.order_item_movieshowtime)
        var orderHallName = view.findViewById<TextView>(R.id.order_item_hallname)
        var orderTotalPrice = view.findViewById<TextView>(R.id.order_item_ticketprice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_order_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun bind(model: OrderBean) {
            Glide.with(mContext!!).load(model.moviePoster).into(holder.orderMoviePoster)
            holder.orderMovieName.text = "${model.movieName} ${model.ticketNum}"
            holder.orderCinemaName.text = model.cinemaName
            holder.orderMovieShowtime.text = model.movieShowtime
            holder.orderHallName.text = "${model.hallName} ${model.hallLocation}"
            holder.orderTotalPrice.text = "全部：${model.totolPrice}元"
        }

        val item = mItems[position]
        bind(item)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun update(newData: MutableList<OrderBean>) {
        mItems.clear()
        mItems.addAll(newData)
        notifyDataSetChanged()
    }
}