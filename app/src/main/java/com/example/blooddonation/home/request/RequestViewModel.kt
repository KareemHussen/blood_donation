package com.example.blooddonation.home.request

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RequestViewModel(firebase: Firebase) : ViewModel() {
    private val collectionPath = "User Requests"
    private val firestore = firebase.firestore

    private val requestsCollection = firestore.collection(collectionPath)

    //TODO Enable request with authentication and pass user id to document path
    private val authUser = firebase.auth
    val userId = "121"
  //   private val user = authUser.currentUser


    //Make request function to add new request
    suspend fun makeRequest(request: RequestModel) {
        withContext(Dispatchers.IO) {
            //Creating a new document with request phone number till enabling authentication
            //SetOptions.merge gives me optionality to merge previous requests with same phone
            // and later ... with authentication
            val map = hashMapOf<String, Any>()
            //TODO Change dummy user ID to current user id
            map.put("Request", request)
            map.put("Time Stamp", FieldValue.serverTimestamp())
            val document = requestsCollection.document(userId)
            document.get().addOnSuccessListener { doc ->
                if (doc != null) {
                    document.collection("Requests").add(map).addOnSuccessListener {
                        Log.d("Request Creation", "Request added successfully")
                    }.addOnFailureListener() { e ->
                        Log.d("Request Creation", e.message.toString()
                        )
                    }
                } else {
                    document.collection("$collectionPath/$userId").add(map)
                }
            }

        }

    }
    suspend fun retrieveRequests(): LiveData<Task<QuerySnapshot>>{
        val requestsLiveData = MutableLiveData<Task<QuerySnapshot>>()
        withContext(Dispatchers.IO){
            requestsLiveData.value= firestore.collection(collectionPath)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("Document Data", "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("Document Data", "Error getting documents: ", exception)
                }
        }
        return requestsLiveData
    }

}
