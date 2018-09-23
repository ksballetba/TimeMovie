package com.ksballetba.timemovie.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.model.bean.MovieDetailBean

class StagePicAdapter(val mItems:MutableList<MovieDetailBean.Data.Basic.StageImg.X>): RecyclerView.Adapter<StagePicAdapter.ViewHolder>() {
    internal var mContext: Context? = null
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var stagePic = view.findViewById<ImageView>(R.id.video_pic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        if(mContext==null){
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_pic_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        fun bind(model:MovieDetailBean.Data.Basic.StageImg.X){
            Glide.with(mContext!!).load(model.imgUrl).into(holder.stagePic)
        }
        val item = mItems[position]
        bind(item)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun update(newData:MutableList<MovieDetailBean.Data.Basic.StageImg.X>){
        mItems.clear()
        mItems.addAll(newData)
        notifyDataSetChanged()
    }
}