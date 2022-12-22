package com.example.blooddonation.util

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.example.blooddonation.R
import com.squareup.picasso.Picasso


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()

        Picasso.get().load(imgUri).apply {
            placeholder(R.drawable.rectangle_27)
            error(com.google.android.material.R.drawable.mtrl_ic_error)
                .resize(0,180).into(imgView)
        }
    }
}