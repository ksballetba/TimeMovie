package com.ksballetba.timemovie.mvp.model.bean

data class CinemaDetailBean(
        val cinemaId: Int, // 6022
        val namecn: String, // 武汉金逸影城江夏店
        val address: String, // 武汉市江夏区江夏大道与北华街交汇处九全嘉购物广场第四层
        val cityid: Int, // 561
        val telphone: String, // 027-81787990
        val longitude: Double, // 0
        val latidude: Double, // 0
        val rating: Double, // 0
        val roadline: String, // 乘坐公车J1、J4、J6、J7、902、903、908、918至世纪广场  站到达
        val movieCount: Int, // 9
        val showtimeCount: Int, // 44
        val currentDate: String, // 2018-10-11
        val today: String, // 2018-10-11
        val valueDates: List<ValueDate>,
        val movies: List<Movy>,
        val showtimes: List<Showtime>,
        val totalMovieCount: Int, // 15
        val totalShowtimeCount: Int, // 174
        val haveOnlineTicket: Boolean // true
) {
    data class Movy(
            val movieId: Int, // 236488
            val movieTitleCn: String, // 古剑奇谭之流月昭明
            val movieTitleEn: String, // Legend of the Ancient Sword
            val coverSrc: String, // http://img5.mtime.cn/mt/2018/09/26/104343.16276012_182X243X4.jpg
            val bigRating: Int, // 5
            val smallRating: Int, // 8
            val trailerId: Int, // 71952
            val director: String, // 雷尼·哈林
            val actor: String, // 王力宏
            val actor2: String, // 宋茜
            val runtime: String, // 105分钟
            val property: String, // 奇幻/动作
            val viewProperty: String, // 奇幻/动作
            val movieDetailUrl: String, // http://movie.mtime.com/236488/
            val year: String // 2018
    )

    data class Showtime(
            val id: Int, // 1877262830
            val showtimeId: Int, // 410564360
            val movieId: Int, // 242119
            val realtime: String, // October, 11 2018 22:50:00
            val specialVersion: String,
            val version: String, // 2D
            val language: String, // 中文版
            val movieEndTime: String, // 预计00:45散场
            val hallId: Int, // 37540
            val hallName: String, // 3号厅（自备3D眼镜）
            val seatCount: Int, // 121
            val isSale: Boolean, // true
            val price: String,
            val servicePrice: Int, // 0
            val mtimePrice: Int, // 34
            val seatUrl: String, // http://piao.mtime.com/onlineticket/showtime/1877262830/
            val isContinueShowtime: Boolean, // false
            val continueMovies: List<Any>,
            val isMorrowShowtime: Boolean, // false
            val specialTitle: String
    )

    data class ValueDate(
            val date: String, // October, 14 2018 00:00:00
            val dateUrl: String // http://theater.mtime.com/China_Hubei_Province_Wuhan_JiangXiaQu/6022/?d=20181014
    )
}