package com.ksballetba.timemovie.mvp.model.bean

data class BookingMovieBean(
        val count: Int, // 15
        val movies: List<Movy>,
        val totalCinemaCount: Int, // 124
        val totalComingMovie: Int, // 49
        val totalHotMovie: Int // 48
) {
    data class Movy(
            val actorName1: String, // 牛骏峰
            val actorName2: String, // 白举纲
            val btnText: String,
            val commonSpecial: String, // "塑料兄弟情"经历旅行生死考验
            val directorName: String, // 张承
            val img: String, // http://img5.mtime.cn/mt/2018/09/17/153354.42749849_1280X720X2.jpg
            val is3D: Boolean, // false
            val isDMAX: Boolean, // false
            val isFilter: Boolean, // false
            val isHot: Boolean, // false
            val isIMAX: Boolean, // false
            val isIMAX3D: Boolean, // false
            val isNew: Boolean, // false
            val length: Int, // 106
            val movieId: Int, // 258535
            val nearestShowtime: NearestShowtime,
            val preferentialFlag: Boolean, // false
            val rDay: Int, // 14
            val rMonth: Int, // 9
            val rYear: Int, // 2018
            val ratingFinal: Float, // -1
            val titleCn: String, // 说走就走之不说再见
            val titleEn: String, // BORNTO BE WILD: THE GRADUATION TRIP
            val type: String, // 爱情 / 冒险 / 喜剧
            val wantedCount: Int // 46
    ) {
        data class NearestShowtime(
                val isTicket: Boolean, // true
                val nearestCinemaCount: Int, // 44
                val nearestShowDay: Int, // 1537344000
                val nearestShowtimeCount: Int // 72
        )
    }
}