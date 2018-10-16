package com.ksballetba.timemovie.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.model.bean.CinemaDetailBean

class CinemaDetailMovieAdapter(val mItems:MutableList<CinemaDetailBean.Movy>, internal val didSelectedAtPos:(idx:Int)->Unit): RecyclerView.Adapter<CinemaDetailMovieAdapter.ViewHolder>() {
    internal var mContext: Context? = null
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var moviePic = view.findViewById<ImageView>(R.id.movie_pic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        if(mContext==null){
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_moviepic_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        fun bind(model:CinemaDetailBean.Movy){
            Glide.with(mContext!!).load(model.coverSrc).into(holder.moviePic)
            with(holder.moviePic){
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

    fun update(newData:MutableList<CinemaDetailBean.Movy>){
        mItems.clear()
        mItems.addAll(newData)
        notifyDataSetChanged()
    }
}