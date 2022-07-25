package com.example.myapplication.utils

import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myapplication.R
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * Created by msaycon on 18,Jul,2022
 */

fun ImageView.loadFromUrl(url: String) =
    GlideApp.with(this.context.applicationContext)
        .load(url)
        .transform(FitCenter())
        .transition(DrawableTransitionOptions.withCrossFade())
        .placeholder(R.drawable.ic_baseline_image_24)
        .error(R.drawable.ic_baseline_image_not_supported_24)
        .timeout(30000)
        .into(this)


fun ImageView.loadBackgroundFromUrl(url: String) =
    GlideApp.with(this.context.applicationContext)
        .load(url)
        .transform(CenterCrop(), BlurTransformation())
        .transition(DrawableTransitionOptions.withCrossFade())
        .placeholder(R.drawable.ic_baseline_image_24)
        .error(R.drawable.ic_baseline_image_not_supported_24)
        .timeout(30000)
        .into(this)