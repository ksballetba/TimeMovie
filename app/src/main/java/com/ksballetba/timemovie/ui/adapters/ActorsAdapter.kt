package com.ksballetba.timemovie.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.model.bean.MovieDetailBean

class ActorsAdapter(val mItems:MutableList<MovieDetailBean.Data.Basic.Actor>): RecyclerView.Adapter<ActorsAdapter.ViewHolder>() {
    internal var mContext: Context? = null
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var actorPic = view.findViewById<ImageView>(R.id.actor_item_img)
        var actorName = view.findViewById<TextView>(R.id.actor_item_name)
        var actorPos = view.findViewById<TextView>(R.id.actor_item_pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        if(mContext==null){
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_actors_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        fun bind(model:MovieDetailBean.Data.Basic.Actor){
            holder.actorName.text = model.name
            Glide.with(mContext!!).load(model.img).into(holder.actorPic)
            holder.actorPos.text = model.roleName
        }
        val item = mItems[position]
        bind(item)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun update(newData:MutableList<MovieDetailBean.Data.Basic.Actor>){
        mItems.clear()
        mItems.addAll(newData)
        notifyDataSetChanged()
    }
}