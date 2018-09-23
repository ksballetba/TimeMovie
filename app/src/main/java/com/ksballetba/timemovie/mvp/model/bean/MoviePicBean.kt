package com.ksballetba.timemovie.mvp.model.bean

data class MoviePicBean(
        val images: List<Image>,
        val imageTypes: List<ImageType>
) {
    data class Image(
            val id: Int, // 7315536
            val image: String, // http://img5.mtime.cn/pi/2016/12/21/112702.53654087_1000X1000.jpg
            val type: Int, // 41
            val approveStatus: Int, // 1
            val imageSubtype: Int, // 4101
            val imageSubtypeDes: String // 官方工作照
    )

    data class ImageType(
            val type: Int, // 21
            val typeName: String // 封套
    )
}