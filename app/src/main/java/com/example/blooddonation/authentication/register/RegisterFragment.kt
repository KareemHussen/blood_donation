package com.example.blooddonation.authentication.register

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.blooddonation.R
import com.example.blooddonation.databinding.FragmentRegisterBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        loginClicked()
        registerClicked()

        return binding.root
    }

    private fun loginClicked() {
        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun registerClicked() {
        binding.btnRegister.setOnClickListener {
            if (
                checkDataFilled(binding.edtUserName, binding.tilUserName)
                && checkEmailContent(binding.edtEmail, binding.tilEmail)
                && checkDataFilled(binding.edtPassword, binding.tilPassword)
                && checkDataFilled(binding.edtPhone, binding.tilPhone)
                && checkDataFilled(binding.edtBlood, binding.tilBlood)
                && checkDataFilled(binding.edtLocation, binding.tilLocation)
            )
                firebaseAuthentication()
            else
                Toast.makeText(
                    requireContext(),
                    "Please Fill email and pass",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    private fun checkEmailContent(edt: TextInputEditText, container: TextInputLayout): Boolean =
        if (edt.text.toString().isEmpty()) {
            container.helperText = "Require"
            false
        } else if (Patterns.EMAIL_ADDRESS.matcher(edt.text.toString()).matches()) {
            container.helperText = null
            true
        } else {
            container.helperText = "Invalid EmailAddress"
            false
        }

    private fun checkDataFilled(edt: TextInputEditText, container: TextInputLayout): Boolean =
        if (edt.text.toString().isEmpty()) {
            container.helperTextCurrentTextColor
            container.helperText = "Require"
            false
        } else {
            container.helperText = null
            true
        }

    private fun firebaseAuthentication() {
        Firebase.auth.createUserWithEmailAndPassword(
            binding.edtEmail.text.toString(),
            binding.edtPassword.text.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                saveDataToFireBase()
                Toast.makeText(
                    requireContext(),
                    "Registration Done Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)

            } else {
                Toast.makeText(
                    requireContext(),
                    "Registration Failed: ${it.exception}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun saveDataToFireBase() {
        val userId = Firebase.auth.currentUser?.uid!!
        val database: DatabaseReference = Firebase.database.reference

        database.child("Users").child(userId).child("PhoneNumber")
            .setValue(binding.edtPhone.text.toString())

        database.child("Users").child(userId).child("UserName")
            .setValue(binding.edtUserName.text.toString())

        database.child("Users").child(userId).child("BloodType")
            .setValue(binding.edtBlood.text.toString())

        database.child("Users").child(userId).child("Location")
            .setValue(binding.edtLocation.text.toString())

    }

}