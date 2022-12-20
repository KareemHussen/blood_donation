package com.example.blooddonation.authentication.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.blooddonation.R
import com.example.blooddonation.databinding.FragmentRegisterBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPassword.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty())
                firebaseAuthentication(email, pass)
            else
                Toast.makeText(
                    requireContext(),
                    "Please Fill email and pass",
                    Toast.LENGTH_SHORT
                ).show()
        }

        return binding.root
    }

    private fun firebaseAuthentication(email: String, pass: String) {
        Firebase.auth.createUserWithEmailAndPassword(
            email, pass
        ).addOnCompleteListener {
            if (it.isSuccessful) {
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
}