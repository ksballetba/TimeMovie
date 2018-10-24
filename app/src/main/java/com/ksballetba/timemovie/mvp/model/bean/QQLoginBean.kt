package com.ksballetba.timemovie.mvp.model.bean

import com.google.gson.annotations.SerializedName

data class QQLoginBean(
        val ret: Int, // 0
        val openid: String, // F0D30645D1A01FB74618DE4240F6C9E0
        @SerializedName("access_token")
        val accessToken: String, // 7E88AA7A99E3D47DC48EC0BDD193B2DC
        @SerializedName("pay_token")
        val payToken: String, // 877EB0CC7C543911D2779525B79D4C0C
        @SerializedName("expires_in")
        val expiresIn: Int, // 7776000
        val pf: String, // desktop_m_qq-10000144-android-2002-
        val pfkey: String, // 7b7b999b4468de740ba58351ce90b177
        val msg: String,
        @SerializedName("login_cost")
        val loginCost: Int, // 85
        @SerializedName("query_authority_cost")
        val queryAuthorityCost: Int, // 326
        @SerializedName("authority_cost")
        val authorityCost: Int, // 0
        @SerializedName("expires_time")
        val expiresTime: Long // 1547967799936
)