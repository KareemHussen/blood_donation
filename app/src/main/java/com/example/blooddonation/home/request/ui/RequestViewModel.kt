package com.example.blooddonation.home.request.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.blooddonation.home.request.model.RequestModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date

class RequestViewModel(firebase: Firebase) : ViewModel() {
    private val collectionPath = "Requests"
    private val firestore = firebase.firestore
    private val requestsCollection = firestore.collection(collectionPath)
    private val authUser = firebase.auth
    private val userId = authUser.currentUser?.uid

    //Make request function to add new request
    fun makeRequest(request: RequestModel) =
        CoroutineScope(Dispatchers.IO).launch {

            if (userId != null) {
                request.name = userId
            }
            request.timeRequest = java.util.Calendar.getInstance().time.toString()
            val document = requestsCollection.document()
            document.get().addOnSuccessListener { doc ->
                if (doc != null) {
                    document.set(request).addOnSuccessListener {
                        Log.d("Request Creation", "Request added successfully")
                    }.addOnFailureListener() { e ->
                        Log.d(
                            "Request Creation", e.message.toString()
                        )
                    }
                } else {
                    document.collection("$collectionPath/$userId").add(request)
                }
            }

        }


}
