package com.ksballetba.timemovie.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.mvp.model.bean.MovieCommentsBean

class MovieCommentsAdapter(val mItems:MutableList<MovieCommentsBean.Data.Ct>): RecyclerView.Adapter<MovieCommentsAdapter.ViewHolder>() {
    internal var mContext: Context? = null
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var commentAvatar = view.findViewById<ImageView>(R.id.comment_item_avatar)
        var commentNickname = view.findViewById<TextView>(R.id.comment_item_nickname)
        var commentSum = view.findViewById<TextView>(R.id.comment_item_sum)
        var commentRating = view.findViewById<RatingBar>(R.id.comment_item_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        if(mContext==null){
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_comment_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        fun bind(model:MovieCommentsBean.Data.Ct){
            Glide.with(mContext!!).load(model.caimg).into(holder.commentAvatar)
            holder.commentNickname.text = model.ca
            holder.commentSum.text = model.ce
            holder.commentRating.rating = model.cr.toFloat()/2
        }
        val item = mItems[position]
        bind(item)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun update(newData:MutableList<MovieCommentsBean.Data.Ct>){
        mItems.clear()
        mItems.addAll(newData)
        notifyDataSetChanged()
    }
}