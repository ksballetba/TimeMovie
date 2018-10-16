package com.ksballetba.timemovie.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.model.bean.CinemaBean

class ChooseCinemaAdapter(val mItems:MutableList<CinemaBean>, internal val didSelectedAtPos:(idx:Int)->Unit): RecyclerView.Adapter<ChooseCinemaAdapter.ViewHolder>() {
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
        fun bind(model:CinemaBean){
            holder.cinemaItemTitle.text = model.cname
            holder.cinemaItemLocation.text = model.address
            holder.cinemaItemDistance.text = model.dsname
            holder.cinemaItemTel.text = if(model.promotions.isNotEmpty()) model.promotions[0].description else "暂无优惠"
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

    fun update(newData:MutableList<CinemaBean>){
        mItems.clear()
        mItems.addAll(newData)
        notifyDataSetChanged()
    }
}