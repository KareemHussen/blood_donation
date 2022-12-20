package com.example.blooddonation.home.request

import android.os.Bundle
import android.provider.CalendarContract.Calendars
import android.text.Editable
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.blooddonation.R
import com.example.blooddonation.databinding.FragmentCreateRequestBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class CreateRequest : Fragment() {
    private lateinit var binding: FragmentCreateRequestBinding
    private  val requestVm: RequestViewModel by activityViewModels {
        RequestViewModelFactory(Firebase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentCreateRequestBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener {
            binding.mobileNumTextLayout.helperText = checkPhoneNumberValidation(binding.mobileNumET).errorMessage
            emptyFieldsChecking()
            val request = makeNewRequest()
            CoroutineScope((Dispatchers.IO)).launch {
                if (request != null) {
                    requestVm.makeRequest(request)
                }
            }
        }
        return binding.root
    }

    private fun makeNewRequest(): RequestModel? {
        val city = binding.cityEditText.text.toString()
        val hospital = binding.hospitalET.text.toString()
        val bloodType = binding.bloodTypeET.text.toString()
        val notes: String = binding.notesET.text.toString()
        if (binding.mobileNumET.text!!.isNotEmpty()) {
            val phoneNumber = Integer.parseInt(binding.mobileNumET.text.toString())
            return if (notes.isEmpty()) {
                RequestModel(city, hospital, bloodType, phoneNumber, "Not specified notes")
            } else {
                RequestModel(city, hospital, bloodType, phoneNumber, notes)
            }
        }
        return null
    }

    private fun emptyFieldsChecking() {
        val arrayOfEditable = arrayOf(binding.cityEditText, binding.hospitalET, binding.mobileNumET, binding.bloodTypeET)
        val arrayOfLayouts = arrayOf(binding.cityContainerLayout, binding.hospitalContainer, binding.mobileNumTextLayout, binding.bloodTypeContainer)
        for (editable in arrayOfEditable.indices) {
            if (arrayOfEditable[editable].text.isEmpty()) {
                arrayOfLayouts[editable].helperText= requireContext().getString(R.string.required)
            }
        }
    }
    private fun checkPhoneNumberValidation(editText: EditText): Result {
        val phoneNumber = editText.text.toString()
        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            return Result(success = false, "Invalid Type please enter a phone number")
        }
        if (phoneNumber.length < 10) {
            Toast.makeText(requireContext(), "phoneNumber.length<10", Toast.LENGTH_SHORT).show()
            return Result(
                success = false,
                errorMessage = "Mobile number should be at least 10 characters"
            )
        }
        for (c in phoneNumber.indices) {
            if (c == 0) {
                if (phoneNumber[c] != '1') {
                    Toast.makeText(requireContext(), "phoneNumber[c]!='1'", Toast.LENGTH_SHORT).show()

                    return Result(
                        success = false,
                        errorMessage = "Not a valid Egyptian phone number"
                    )
                }
            } else if (c == 1) {
                if (phoneNumber[c] != '1' && phoneNumber[c] != '2' && phoneNumber[c] != '0' && phoneNumber[c] != '5') {
                    Toast.makeText(requireContext(), phoneNumber[c].toString(), Toast.LENGTH_SHORT).show()

                    return Result(
                        success = false,
                        errorMessage = "Not a valid Egyptian phone number"
                    )
                }
            }
        }
        return Result(true, null)
    }

}

data class Result(
    val success: Boolean,
    val errorMessage: String?
)
