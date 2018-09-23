package com.ksballetba.timemovie.mvp.model.bean

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class MovieDetailBean(
    val code: String, // 1
    val data: Data,
    val msg: String, // 成功
    val showMsg: String
) {
    data class Data(
        val advertisement: Advertisement,
        val basic: Basic,
        val boxOffice: BoxOffice,
        val live: Live,
        val playState: String, // 2
        val playlist: List<Playlist>,
        val related: Related
    ) {
        data class Advertisement(
            val advList: List<Adv>,
            val count: Int, // 1
            val error: String,
            val success: Boolean // true
        ) {
            data class Adv(
                val advTag: String, // 广告
                val endDate: Int, // 1548172799
                val isHorizontalScreen: Boolean, // false
                val isOpenH5: Boolean, // false
                val startDate: Int, // 1514736000
                val tag: String, // 商城T恤促销+商城手机壳促销
                val type: String, // 203
                val typeName: String, // 影片详情页banner2
                val url: String // https://static1.mtime.cn/feature/mobile/banner/2018/0910/txsjk750210.html
            )
        }

        data class Playlist(
            val isOpenByBrowser: Boolean, // false
            val payRule: String, // VIP免费
            val picUrl: String, // http://img5.mtime.cn/mg/2018/07/16/105243.30739031.jpg
            val playSourceName: String, // 腾讯视频
            val playUrl: String, // https://m.v.qq.com/cover/f/fn2vxjrncbi0wtb.html_m
            val playUrlH5: String, // https://m.v.qq.com/cover/f/fn2vxjrncbi0wtb.html_m
            val playUrlPC: String, // https://v.qq.com/x/cover/fn2vxjrncbi0wtb.html
            val sourceId: String // 62353
        )

        data class BoxOffice(
            val movieId: Int, // 125805
            val ranking: Int, // 0
            val todayBox: Int, // 0
            val todayBoxDes: String,
            val todayBoxDesUnit: String,
            val totalBox: Long, // 112780409590
            val totalBoxDes: String, // 11.28亿元
            val totalBoxUnit: String // 累计票房(亿)
        )

        data class Live(
            val count: Int, // 1
            val img: String, // http://img5.mtime.cn/mg/2017/02/04/165331.18709160.jpg
            val liveId: Int, // 224
            val playNumTag: String, // 83.5万次播放
            val playTag: String,
            val status: Int, // 4
            val title: String // 电影《极限特工：终极回归》中国首映礼
        )

        data class Basic(
            val actors: List<Actor>,
            val award: Award,
            val bigImage: String,
            val commentSpecial: String,
            val community: Any?,
            val director: Director,
            val festivals: List<Any>,
            val hotRanking: Int, // -1
            val img: String, // http://img5.mtime.cn/mt/2017/01/05/105822.16893974_1280X720X2.jpg
            val is3D: Boolean, // true
            val isDMAX: Boolean, // true
            val isEggHunt: Boolean, // false
            val isFilter: Boolean, // false
            val isIMAX: Boolean, // false
            val isIMAX3D: Boolean, // true
            val isTicket: Boolean, // false
            val message: String, // 该操作将清除您对该片的评分！是否确认？
            val mins: String, // 107分钟
            val movieId: Int, // 125805
            val movieStatus: Int, // 1
            val name: String, // 极限特工：终极回归
            val nameEn: String, // xXx: The Return of Xander Cage
            val overallRating: Double, // 6.6
            val personCount: Int, // 228
            val quizGame: Any?,
            val releaseArea: String, // 中国
            val releaseDate: String, // 20170210
            val sensitiveStatus: Boolean, // false
            val showCinemaCount: Int, // -1
            val showDay: Int, // -1
            val showtimeCount: Int, // -1
            val stageImg: StageImg,
            val story: String, // 范·迪塞尔扮演的桑德·凯奇在被认为已经死亡后又奇迹般归来，极度危险的反派研制出了能够毁灭世界的终极武器，在这一大危机下，凯奇重出江湖，并招募了一支各有所长、热爱刺激的团队。他们与甄子丹领衔的另一队人马争夺一个叫做“潘多拉魔盒”的武器，最终一起携手拯救世界。才华横溢的导演D·J·卡卢索动作场景拍得非常精彩，其中包括滑滑板下坡的镜头、摩托车冲浪的场景、一场徒步高速公路追逐戏、零重力飞机上的搏斗片段，还有可以终结一切枪战的boss级枪战。
            val style: Style,
            val totalNominateAward: Int, // 0
            val totalWinAward: Int, // 0
            val type: List<String>,
            val url: String, // https://movie.mtime.com/125805/
            val video: Video
        ) {

            data class Video(
                val count: Int, // 40
                val hightUrl: String, // https://vfx.mtime.cn/Video/2017/01/05/mp4/170105105137886980.mp4
                val img: String, // http://img5.mtime.cn/mg/2017/01/05/105124.57142324_235X132X4.jpg
                val title: String, // 极限特工：终极回归 中国版预告片
                val url: String, // https://vfx.mtime.cn/Video/2017/01/05/mp4/170105105137886980_480.mp4
                val videoId: Int, // 64107
                val videoSourceType: Int // 1
            )

            data class Style(
                val isLeadPage: Int, // 0
                val leadImg: String, // https://img2.mtime.cn/mg/.jpg
                val leadUrl: String
            )

            data class Director(
                val directorId: Int, // 903521
                val img: String, // http://img5.mtime.cn/ph/2017/02/17/182200.43454182_1280X720X2.jpg
                val name: String, // D·J·卡卢索
                val nameEn: String // D.J. Caruso
            )

            data class Award(
                val awardList: List<Any>,
                val totalNominateAward: Int, // 0
                val totalWinAward: Int // 0
            )

            data class Actor(
                val actorId: Int, // 1023991
                val img: String, // http://img5.mtime.cn/ph/2017/01/05/144625.10720932_1280X720X2.jpg
                val name: String,
                val nameEn: String, // Gary 'Si-Jo' Foo
                val roleImg: String,
                val roleName: String // NSA Contractor       (uncredited)
            )

            data class StageImg(
                val count: Int, // 198
                val list: List<X>
            ) {
                data class X(
                    val imgId: Int, // 7326887
                    val imgUrl: String // http://img5.mtime.cn/pi/2017/01/25/165625.43047270_1280X720X2.jpg
                )
            }
        }

        data class Related(
            val goodsCount: Int, // 0
            val goodsList: List<Any>,
            val relateId: Int, // 0
            val relatedUrl: String, // https://mall-wv.mtime.cn/#!/commerce/list/
            val type: Int // 0
        )
    }
    class Deserializer : ResponseDeserializable<MovieDetailBean> {
        override fun deserialize(content: String): MovieDetailBean? {
            return Gson().fromJson(content,MovieDetailBean::class.java)
        }
    }
}