package com.example.blooddonation.home.request.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.firebase.ktx.Firebase

class RequestViewModelFactory (private val firebase: Firebase): ViewModelProvider.Factory {
    override fun <T:ViewModel> create (modelClass: Class<T>,extras:CreationExtras):T {
        if (modelClass.isAssignableFrom(RequestViewModel::class.java)){
            return RequestViewModel(firebase) as T
        }
        throw IllegalArgumentException("View Model not found")
    }

}