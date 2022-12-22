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
import androidx.navigation.fragment.findNavController
import com.example.blooddonation.R
import com.example.blooddonation.databinding.FragmentProfileBinding
import com.example.blooddonation.databinding.InfoBlockBinding
import com.example.blooddonation.domain.User
import kotlin.math.absoluteValue


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val user = User(
            "Kevin Durant",
            "1a2s3d%$]'",
            "+108005473614",
            "Brooklyn, NYC, New York",
            "A+",
            5,
            2,
            true,
            "https://cdn.nba.com/headshots/nba/latest/1040x760/201142.png"
        )

        binding.user = user

        val donationsGivenStr = if (user.donationsGiven > 9)
            user.donationsGiven.toString()
        else "0" + user.donationsGiven.toString()

        val donationsRequestedStr = if (user.donationsRequested > 9)
            user.donationsRequested.toString()
        else "0" + user.donationsRequested.toString()

        val infoList = listOf(
            Pair(user.bloodType, "Blood Type"),
            Pair(donationsGivenStr, "Donated"),
            Pair(donationsRequestedStr, "Requested"),
        )

        val linearLayout = binding.userInfoLayout
        for (block in infoList) {

            val blockBinding: InfoBlockBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.info_block, null, false)

            blockBinding.infoText.text = block.first
            blockBinding.infoTypeText.text = block.second

            linearLayout.addView(blockBinding.root)
        }

        binding.editBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

    }

}