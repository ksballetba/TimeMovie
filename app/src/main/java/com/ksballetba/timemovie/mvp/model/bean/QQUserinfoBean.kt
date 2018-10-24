package com.ksballetba.timemovie.mvp.model.bean

import com.google.gson.annotations.SerializedName

data class QQUserinfoBean(
        val ret: Int, // 0
        val msg: String,
        @SerializedName("is_lost")
        val isLost: Int, // 0
        val nickname: String, // 橙子爱樱花
        val gender: String, // 男
        val province: String, // 湖北
        val city: String, // 武汉
        val year: String, // 1998
        val constellation: String,
        val figureurl: String, // http://qzapp.qlogo.cn/qzapp/101510659/F0D30645D1A01FB74618DE4240F6C9E0/30
        @SerializedName("figureurl_1")
        val figureurl1: String, // http://qzapp.qlogo.cn/qzapp/101510659/F0D30645D1A01FB74618DE4240F6C9E0/50
        @SerializedName("figureurl_2")
        val figureurl2: String, // http://qzapp.qlogo.cn/qzapp/101510659/F0D30645D1A01FB74618DE4240F6C9E0/100
        @SerializedName("figureurl_qq_1")
        val figureurlQq1: String, // http://thirdqq.qlogo.cn/qqapp/101510659/F0D30645D1A01FB74618DE4240F6C9E0/40
        @SerializedName("figureurl_qq_2")
        val figureurlQq2: String, // http://thirdqq.qlogo.cn/qqapp/101510659/F0D30645D1A01FB74618DE4240F6C9E0/100
        @SerializedName("is_yellow_vip")
        val isYellowVip: String, // 0
        val vip: String, // 0
        @SerializedName("yellow_vip_level")
        val yellowVipLevel: String, // 0
        val level: String, // 0
        @SerializedName("is_yellow_year_vip")
        val isYellowYearVip: String // 0
)