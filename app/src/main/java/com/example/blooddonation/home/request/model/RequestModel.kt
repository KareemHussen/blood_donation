package com.example.blooddonation.home.request.model

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class RequestModel(
    val city: String, val bloodType: String,
    val hospital: String,
    val phoneNumber: Int,
    var name:String="",
    var date:Date?=null,
)

