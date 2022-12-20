package com.example.blooddonation.home.request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blooddonation.R
import com.example.blooddonation.databinding.FragmentDonationRequestBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DonationRequestFragment : Fragment() {
    private var projectArrayList = ArrayList<Request>()
    private val donationRequestRef = Firebase.firestore.collection("requests")
    var data = ArrayList<Request>()

    private val requestsAdapter =
        RequestsAdapter(RequestsAdapter.RequestListener { request ->
            onClicked(request)
        })

    private lateinit var binding: FragmentDonationRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_donation_request,
                container, false
            )


        binding.apply {
            RVdonation.apply {
                adapter = requestsAdapter
                layoutManager = LinearLayoutManager(this.context)
            }

        }

        retrieveRequests()

        return binding.root
    }


    private fun retrieveRequests() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val querySnapshot = donationRequestRef.get().await()
                for (document in querySnapshot.documents) {
                    val request = document.toObject<Request>()
                    data.add(request!!)
                }
                withContext(Dispatchers.Main) {
                    requestsAdapter.submitList(data)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun onClicked(request: Request) {
    }

}

data class ItemsViewModel(val text: String) {
}
