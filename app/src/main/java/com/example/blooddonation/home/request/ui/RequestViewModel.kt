package com.example.blooddonation.home.request.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.blooddonation.home.request.model.RequestModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RequestViewModel(firebase: Firebase) : ViewModel() {
    private val collectionPath = "Requests"
    private val firestore = firebase.firestore
    private val requestsCollection = firestore.collection(collectionPath)

    //TODO Enable request with authentication and pass user id to document path
    private val authUser = firebase.auth
    val userId = "121"
  //   private val user = authUser.currentUser
    //Make request function to add new request
     fun makeRequest(request: RequestModel) =
        CoroutineScope(Dispatchers.IO).launch {
            //Creating a new document with request phone number till enabling authentication
            //SetOptions.merge gives me optionality to merge previous requests with same phone
            // and later ... with authentication
            val map = hashMapOf<String, Any>()
            //TODO Change dummy user ID to current user id
            map.put("Request", request)
            map.put("Time Stamp", FieldValue.serverTimestamp())
            map.put("User Id",userId)
            val document = requestsCollection.document()
            document.get().addOnSuccessListener { doc ->
                if (doc != null) {
                    document.set(map).addOnSuccessListener {
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


    suspend fun retrieveRequests():ArrayList<HashMap<String,Any>>{
        return withContext(Dispatchers.IO){
            val requestsList=ArrayList<HashMap<String,Any>>()
            try {
                val querySnapshot=requestsCollection.get().await()
                for (document in querySnapshot.documents){
                    val request: HashMap<String, Any> = document.data as HashMap<String, Any>
                    requestsList.add(request)
                    Log.d("Requests Retrieval ","Requests retrieved successfully")
                }
            }catch (e:Exception){
                Log.e("Requests Retrieval ",e.message.toString())
            }
            Log.d("Requests Retrieval",requestsList.get(0).toString())
            requestsList
        }
    }


}
