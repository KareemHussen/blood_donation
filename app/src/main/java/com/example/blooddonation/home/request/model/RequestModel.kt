package com.example.blooddonation.home.request.model

data class RequestModel(
    var name: String = "",
    var city: String = "",
    var hospital: String = "",
    var bloodType: String = "",
    var mobileNumber: String = "",
    var note: String = "",
    var timeRequest :String = "",
)

