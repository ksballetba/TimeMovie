package com.ksballetba.timemovie.mvp.model.bean

data class MovieVideoBean(
        val totalPageCount: Int, // 1
        val totalCount: Int, // 17
        val videoList: List<Video>
) {
    data class Video(
            val id: Int, // 64284
            val sourceType: Int, // 1
            val url: String, // https://vfx.mtime.cn/Video/2017/01/18/mp4/170118113206598838_480.mp4
            val hightUrl: String, // https://vfx.mtime.cn/Video/2017/01/18/mp4/170118113206598838.mp4
            val image: String, // http://img5.mtime.cn/mg/2017/01/18/111104.16810177_235X132X4.jpg
            val title: String, // 功夫瑜伽 拜年版主题曲MV
            val type: Int, // 5
            val length: Int // 207
    )
}