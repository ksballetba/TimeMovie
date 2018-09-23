package com.ksballetba.timemovie.mvp.model.bean

import com.google.gson.annotations.SerializedName

data class ShowingMovieBean(
        val bImg: String, // http://img5.mtime.cn/mg/2018/09/04/124630.14485487.jpg
        val date: String, // 2018-09-19
        val hasPromo: Boolean, // false
        val lid: Int, // 561
        val ms: List<M>,
        val newActivitiesTime: Int, // 0
        val promo: Any?,
        val totalComingMovie: Int, // 52
        val voucherMsg: String
) {
    data class M(
            @SerializedName("NearestCinemaCount")
            val nearestCinemaCount: Int, // 1
            @SerializedName("NearestDay")
            val nearestDay: Int, // 1537344000
            @SerializedName("NearestShowtimeCount")
            val nearestShowtimeCount: Int, // 3
            val aN1: String, // 谭卓
            val aN2: String, // 李至正
            val actors: String, // 谭卓 / 李至正 / 孙亦沐 / 张定涵
            val cC: Int, // 1
            val commonSpecial: String, // 谭卓演绎高原秘恋传说
            val d: String, // 108
            val dN: String, // 李燕宁
            val def: Int, // 0
            val id: Int, // 242294
            val img: String, // http://img5.mtime.cn/mt/2018/08/28/061507.89808238_1280X720X2.jpg
            val is3D: Boolean, // false
            val isDMAX: Boolean, // false
            val isFilter: Boolean, // false
            val isHasTrailer: Boolean, // true
            val isHot: Boolean, // false
            val isIMAX: Boolean, // false
            val isIMAX3D: Boolean, // false
            val isNew: Boolean, // false
            val isTicket: Boolean, // false
            val m: String,
            val movieId: Int, // 242294
            val movieType: String, // 爱情 / 剧情
            val p: List<String>,
            val preferentialFlag: Boolean, // false
            val r: Float, // -1
            val rc: Int, // 0
            val rd: String, // 20180907
            val rsC: Int, // 0
            val sC: Int, // 3
            val t: String, // 吻隐者
            val tCn: String, // 吻隐者
            val tEn: String, // The Kiss Addict
            val ua: Int, // -1
            val versions: List<Version>,
            val wantedCount: Int, // 30
            val year: String // 2018
    ) {
        data class Version(
                val enum: Int, // 1
                val version: String // 2D
        )
    }
}