package com.ksballetba.timemovie.mvp.model.bean

import com.google.gson.annotations.SerializedName

data class CinemaBean(
        val cid: Int, // 6022
        val cname: String, // 武汉金逸影城江夏店
        val sname: String, // 武汉金逸影城江夏店
        val address: String, // 武汉市江夏区江夏大道与北华街交汇处九全嘉购物广场第四层
        val did: Int, // 3169
        val dsname: String, // 江夏区
        val bid: String, // 0
        val sbid: String,
        val sid: String,
        val isticket: Boolean, // true
        val showtimepage: String, // http://theater.mtime.com/China_Hubei_Province_Wuhan_JiangXiaQu/6022/
        val feature: String, // {CinemaID:6022,Feature3D:1,Feature3DContent:"",FeatureIMAX:0,FeatureIMAXContent:"",FeatureIMAXHeight:0,FeatureIMAXWidth:0,FeatureVIP:0,FeatureVIPContent:"",Feature4D:0,Feature4DContent:"",Feature4DX:0,Feature4DXContent:"",FeatureHuge:0,FeatureHugeContent:"",Feature4K:0,Feature4KContent:"",FeatureDolby:1,FeatureDolbyContent:"2号厅杜比全景  4号厅DTS多声道",FeatureGame:0,FeatureGameContent:"",FeatureFood:1,FeatureFoodCinemaContent:"",FeatureFoodContent:"小牛憨子餐饮、楼兰餐厅、零食小铺等",FeatureLeisure:1,FeatureLeisureCinemaContent:"",FeatureLeisureContent:"网吧、KTV等 ",Loveseat:0,LoveseatContent:"",GlassFor3D:1,GlassFor3DDeposit:0,GlassFor3DContent:"",CardPay:0,CardPayContent:"",Wifi:0,WIFIConent:"",DisabledSeat:0,DisabledSeatContent:"",SelfServiceTicket:1,SelfServiceTicketContent:"影院大厅入口处有自助取票机",SelfServiceTicketImageUrl:"",FeaturePark:0,FeatureParkContent:"",FeatureParkImageUrl:"",IsHallCustomProperty:0,HallCustomPropertyName:"",HallCustomPropertyContent:"",IsServiceCustomProperty:0,ServiceCustomPropertyName:"",ServiceCustomPropertyContent:""}
        val promotions: List<Promotion>,
        val sortid: Int, // 0
        val versions: String, // 1
        val lowestprice: String // 34
) {
    data class Promotion(
            @SerializedName("ID")
            val iD: Int, // 646
            @SerializedName("TagName")
            val tagName: String, // 金逸购票限时减5元
            @SerializedName("Description")
            val description: String, // 金逸购票限时减5元
            @SerializedName("UserType")
            val userType: Int, // 2
            @SerializedName("ActivityType")
            val activityType: Int, // 1
            @SerializedName("URL")
            val uRL: String,
            @SerializedName("SourceID")
            val sourceID: Int, // 1
            @SerializedName("PCURL")
            val pCURL: String
    )
}