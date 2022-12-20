package com.example.blooddonation.home.request

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class RequestModel(
     val city: String, val bloodType: String,
    val hospital: String,
    val phoneNumber: Int
) {
    constructor(city: String, bloodType: String,
                hospital: String,
                phoneNumber: Int, notes: String?) : this(city, bloodType, hospital, phoneNumber)
}


