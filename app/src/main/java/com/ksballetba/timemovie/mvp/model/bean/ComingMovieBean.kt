package com.ksballetba.timemovie.mvp.model.bean

data class ComingMovieBean(
        val attention: List<Attention>,
        val moviecomings: List<Moviecoming>
) {
    data class Moviecoming(
            val actor1: String, // 王迅
            val actor2: String, // 黄小蕾
            val director: String, // 杨真
            val id: Int, // 253914
            val image: String, // http://img5.mtime.cn/mt/2018/06/24/172424.81199925_1280X720X2.jpg
            val isFilter: Boolean, // false
            val isTicket: Boolean, // false
            val isVideo: Boolean, // true
            val locationName: String, // 中国
            val rDay: Int, // 30
            val rMonth: Int, // 11
            val rYear: Int, // 2018
            val releaseDate: String, // 11月30日上映
            val title: String, // 灵魂的救赎
            val type: String, // 剧情
            val videoCount: Int, // 1
            val videos: List<Video>,
            val wantedCount: Int // 20
    ) {
        data class Video(
                val hightUrl: String,
                val image: String, // http://img5.mtime.cn/mg/2018/06/24/132647.53813353.jpg
                val length: Int, // 77
                val title: String, // 灵魂的救赎 定档预告片
                val url: String, // http://vfx.mtime.cn/Video/2018/06/24/mp4/180624132334535572.mp4
                val videoId: Int // 71005
        )
    }

    data class Attention(
            val actor1: String, // 杨幂
            val actor2: String, // 郭京飞
            val director: String, // 刘杰
            val id: Int, // 254854
            val image: String, // http://img5.mtime.cn/mt/2018/09/13/183225.18667083_1280X720X2.jpg
            val isFilter: Boolean, // false
            val isTicket: Boolean, // false
            val isVideo: Boolean, // true
            val locationName: String, // 中国
            val rDay: Int, // 19
            val rMonth: Int, // 10
            val rYear: Int, // 2018
            val releaseDate: String, // 10月19日上映
            val title: String, // 宝贝儿
            val type: String, // 剧情
            val videoCount: Int, // 1
            val videos: List<Video>,
            val wantedCount: Int // 80
    ) {
        data class Video(
                val hightUrl: String,
                val image: String, // http://img5.mtime.cn/mg/2018/09/06/083012.86422925.jpg
                val length: Int, // 15
                val title: String, // 宝贝儿 先导预告
                val url: String, // http://vfx.mtime.cn/Video/2018/09/06/mp4/180906083142712290.mp4
                val videoId: Int // 71850
        )
    }
}