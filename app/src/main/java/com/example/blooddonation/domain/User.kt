package com.example.blooddonation.domain

import android.graphics.Picture

data class User(
    val name: String,
    val password: String,
    val phoneNumber: String,
    val location: String,
    val bloodType: String,
    val donationsGiven: Int,
    val donationsRequested: Int,
    val isAvailable: Boolean,
    val pictureUrl: String
)
