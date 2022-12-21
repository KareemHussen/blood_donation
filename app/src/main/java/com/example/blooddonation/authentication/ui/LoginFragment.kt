package com.example.blooddonation.authentication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.blooddonation.R
import com.example.blooddonation.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var loginBinding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val text =
            "<font color=#FF2156>Dare</font> <font color=#595959>To</font> <font color=#FF2156>Donate</font>"
        loginBinding.donateText.text =
            HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
        loginBinding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        loginBinding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        return loginBinding.root
    }

}