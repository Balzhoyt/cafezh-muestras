package com.balzhoyt.cafezh.muestras.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.request.target.BitmapImageViewTarget


class ImagenCircular (context: Context, imageView: ImageView) : BitmapImageViewTarget(imageView) {

    private val context: Context
    private val imageView: ImageView
    override fun setResource(resource: Bitmap?) {
        val bitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource)
        bitmapDrawable.isCircular = true
        imageView.setImageDrawable(bitmapDrawable)
    }

    init {
        this.context = context
        this.imageView = imageView
    }
}