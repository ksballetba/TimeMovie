package com.ksballetba.timemovie.ui.adapters

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.model.bean.ComingMovieBean
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundDrawable

class HomeComingMovieAdapter(val mItems:MutableList<ComingMovieBean.Moviecoming>, internal val didSelectedAtPos:(idx:Int)->Unit): RecyclerView.Adapter<HomeComingMovieAdapter.ViewHolder>() {
    internal var mContext: Context? = null
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
        fun bind(model:ComingMovieBean.Moviecoming){
            Glide.with(mContext!!).load(model.image).into(holder.movieItemPoster)
            holder.movieItemTitle.text = model.title
            holder.movieItemScore.text = "${model.wantedCount} 人想看"
            with(holder.movieBookButton){
                val filter = PorterDuffColorFilter(ContextCompat.getColor(mContext!!,R.color.wanted_count),PorterDuff.Mode.SRC_ATOP)
                background.colorFilter = filter
                text = "预售"
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

    fun update(newData:MutableList<ComingMovieBean.Moviecoming>){
        mItems.clear()
        mItems.addAll(newData)
        notifyDataSetChanged()
    }
}