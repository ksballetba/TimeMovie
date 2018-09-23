package com.ksballetba.timemovie.ui.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.model.bean.ShowingMovieBean
import org.jetbrains.anko.backgroundColor

class HomeShowingMovieAdapter(val mItems:MutableList<ShowingMovieBean.M>, internal val didSelectedAtPos:(idx:Int)->Unit):RecyclerView.Adapter<HomeShowingMovieAdapter.ViewHolder>() {
    internal var mContext:Context? = null
    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        var movieItem = view.findViewById<LinearLayout>(R.id.movie_item)
        var movieItemPoster = view.findViewById<ImageView>(R.id.movie_item_poster)
        var movieItemTitle = view.findViewById<TextView>(R.id.movie_item_title)
        var movieItemInfo = view.findViewById<TextView>(R.id.movie_item_info)
        var movieItemScore = view.findViewById<TextView>(R.id.movie_item_score)
        var movieBookButton = view.findViewById<Button>(R.id.home_booking_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        if(mContext==null){
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_mian_movie_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        fun bind(model:ShowingMovieBean.M){
            Glide.with(mContext!!).load(model.img).into(holder.movieItemPoster)
            holder.movieItemTitle.text = model.t
            if(model.is3D){
                holder.movieItemInfo.text = " 3D"
            } else{
                holder.movieItemInfo.text = " 2D"
            }
            if(model.r>0){
                holder.movieItemScore.text = "评分 ${model.r}"
                val filter = PorterDuffColorFilter(ContextCompat.getColor(mContext!!,R.color.accent), PorterDuff.Mode.SRC_ATOP)
                holder.movieBookButton.background.colorFilter = filter
                holder.movieBookButton.text = "购票"
            } else{
                holder.movieItemScore.text = "${model.wantedCount} 人想看"
                val filter = PorterDuffColorFilter(ContextCompat.getColor(mContext!!,R.color.wanted_count), PorterDuff.Mode.SRC_ATOP)
                holder.movieBookButton.background.colorFilter = filter
                holder.movieBookButton.text = "预售"
            }
            with(holder.movieBookButton){
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

    fun update(newData:MutableList<ShowingMovieBean.M>){
        mItems.clear()
        mItems.addAll(newData)
        notifyDataSetChanged()
    }
}