package com.ksballetba.timemovie.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.amap.api.services.core.PoiItem
import com.ksballetba.timemovie.R

class CinemaAdapter(val mItems:MutableList<PoiItem>, internal val didSelectedAtPos:(idx:Int)->Unit): RecyclerView.Adapter<CinemaAdapter.ViewHolder>() {
    internal var mContext: Context? = null
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var cinemaItem = view.findViewById<LinearLayout>(R.id.cinema_item)
        var cinemaItemTitle = view.findViewById<TextView>(R.id.cinema_item_name)
        var cinemaItemLocation = view.findViewById<TextView>(R.id.cinema_item_location)
        var cinemaItemDistance= view.findViewById<TextView>(R.id.cinema_item_distance)
        var cinemaItemTel = view.findViewById<TextView>(R.id.cinema_item_tel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        if(mContext==null){
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_cinema_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        fun bind(model:PoiItem){
            holder.cinemaItemTitle.text = model.title
            holder.cinemaItemLocation.text = model.snippet
            holder.cinemaItemDistance.text = "${(model.distance%100)/10.toFloat()}km"
            holder.cinemaItemTel.text = if(!model.tel.isBlank()) model.tel else "暂无数据"
            with(holder.cinemaItem){
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

    fun update(newData:MutableList<PoiItem>){
        mItems.clear()
        mItems.addAll(newData)
        notifyDataSetChanged()
    }
}