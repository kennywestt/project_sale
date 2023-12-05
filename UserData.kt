package com.example.projectsales.model

//회원가입 유저 데이터
data class UserData(
    var id : String = "",
    var email : String ="",
    var name : String ="",
    var birth : String =""
){
    constructor(): this("","","","")
}