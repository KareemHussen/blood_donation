package com.example.blooddonation

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.test.*
import kotlin.math.log


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO('Login then validate email and password !')
        login()
        forgetPassword()
        checkEmail()
        checkPassword()
    }

    private fun checkPassword() {
        etPasswordLogin.setOnFocusChangeListener { view, focus ->
            if (!focus){
                tlPassword.helperText = validEPassword()
            }
        }
    }

    // TODO('What Is This ??!!')
    private fun validEPassword() : String?{
        return null
    }

    private fun checkEmail() {
        etEmailLogin.setOnFocusChangeListener { view, focus ->
            if (!focus){
                tlEmail.helperText = validEmail()
            }
        }
    }

    private fun validEmail() : String?{
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmailLogin.text.toString()).matches()){
            return "Invalid Email"
        }
        return null
    }

    private fun login() {
        btnLogin.setOnClickListener {
            val email = etEmailLogin.text.toString()
            val pass = etPasswordLogin.text.toString()
            if (email.isBlank() || pass.isBlank()) {
                Toast.makeText(activity, "Empty email or password", Toast.LENGTH_SHORT).show()
            } else {
                auth()
            }
        }
    }

    // TODO('What Is This, made a function for just a toast ?')
    private fun forgetPassword(){
        tvForgot_pass.setOnClickListener {
            /*fragmentManager?.beginTransaction()?.apply {
                replace(flFragment.id, ResetFragment())
            }*/
            Toast.makeText(activity, "Forger password", Toast.LENGTH_SHORT).show()
        }
    }
    private fun auth() {
        val auth = FirebaseAuth.getInstance()
        val email = etEmailLogin.text.toString()
        val pass = etPasswordLogin.text.toString()

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                fragmentManager?.beginTransaction()?.apply {
                    replace(flFragment.id, HomeFragment())
                }
            }
            else{
                Toast.makeText(activity,"Wrong Email/Pass" , Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            }
        }

    }
}