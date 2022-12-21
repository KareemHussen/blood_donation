package com.example.blooddonation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.marginStart
import androidx.databinding.DataBindingUtil
import com.example.blooddonation.R
import com.example.blooddonation.databinding.FragmentProfileBinding
import com.example.blooddonation.databinding.InfoBlockBinding
import kotlin.math.absoluteValue


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private val dummyInfoList: List<Pair<String, String>>
        get() {
            return listOf(
                Pair("A+" , "Blood Type"),
                Pair("05" , "Donated"),
                Pair("02" , "Requested"),
            )
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val linearLayout = binding.userInfoLayout
        for (block in dummyInfoList) {
            val blockBinding : InfoBlockBinding = DataBindingUtil.inflate(layoutInflater, R.layout.info_block, null, false)

            blockBinding.infoText.text = block.first
            blockBinding.infoTypeText.text = block.second

            linearLayout.addView(blockBinding.root)
        }
    }

}