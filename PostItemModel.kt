package com.example.projectsales.model

import java.io.Serializable

// 리사이클러 데이터
data class PostItemModel(
    var heading: String= "null",
    var userName: String= "null",
    var post:String="null",
    var date: String= "null",
    var photo: String = "null",
    var salePrice: String= "null",
    var saleExist: String= "null",
    val imageUrl: String= "null"
) : Serializable