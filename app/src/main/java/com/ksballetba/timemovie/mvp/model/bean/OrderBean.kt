package com.ksballetba.timemovie.mvp.model.bean

import org.litepal.crud.LitePalSupport

data class OrderBean(val cinemaName:String,
                val isFinished:Boolean,
                val moviePoster:String,
                val movieName:String,
                val ticketNum:Int,
                val movieShowtime:String,
                val hallName:String,
                val hallLocation:String,
                val totolPrice:Int):LitePalSupport()