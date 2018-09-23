package com.ksballetba.timemovie.mvp.model.bean

data class MovieActorBean(
        val types: List<Type>
) {
    data class Type(
            val typeName: String, // 声音部门
            val typeNameEn: String, // Sound Department
            val persons: List<Person>
    ) {
        data class Person(
                val id: Int, // 2227721
                val name: String,
                val nameEn: String, // Skuli Helgi Sigurgislason
                val image: String // http://img31.mtime.cn/ph/1721/2227721/2227721_1280X720X2.jpg
        )
    }
}