package com.ksballetba.timemovie.mvp.model.bean

data class MovieCommentsBean(
        val code: String, // 1
        val data: Data,
        val msg: String, // 成功
        val showMsg: String
) {
    data class Data(
            val cts: List<Ct>,
            val totalCount: Int, // 1999
            val tpc: Int // 10
    ) {
        data class Ct(
                val ca: String, // 青红皂白
                val caimg: String, // http://img22.mtime.cn/up/2010/11/04/212009.12655552_128X128.jpg
                val cal: String,
                val cd: Int, // 1508799419
                val ce: String, // 最后的剧情 有点偏
                val ceimg: String,
                val commentCount: Int, // 0
                val cr: Double, // 6.6
                val isHot: Boolean, // false
                val tweetId: Int // 44728613
        )
    }
}