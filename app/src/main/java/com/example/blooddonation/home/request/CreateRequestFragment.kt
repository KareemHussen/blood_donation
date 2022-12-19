package com.example.blooddonation.home.request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.blooddonation.R
import com.example.blooddonation.databinding.FragmentCreateRequestBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class CreateRequestFragment : Fragment() {
    private val donationRequestRef = Firebase.firestore.collection("requests")

    private lateinit var binding: FragmentCreateRequestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_create_request, container, false)

        binding.btnSave.setOnClickListener {
            binding.apply {
                val name = etName.text.toString()
                val city = etCity.text.toString()
                val hospital = etHospital.text.toString()
                val bloodType = etBloodType.text.toString()
                val mobile = etMobile.text.toString()
                val addNote = etAddNote.text.toString()

                val request = Request(name,city,hospital,bloodType, mobile,addNote)
                saveRequest(request)
            }
        }
        return  binding.root
    }
    private fun saveRequest(request: Request) = CoroutineScope(Dispatchers.IO).launch {
        try {
            donationRequestRef.add(request).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Successfully saved data.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}