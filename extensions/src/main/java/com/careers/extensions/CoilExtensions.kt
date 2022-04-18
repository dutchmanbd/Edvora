package com.careers.extensions

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.load
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

fun ImageView.load(url: String?, transformation: Transformation = RoundedCornersTransformation()) {
    if (url.isNullOrEmpty()) {
        load(R.drawable.ic_default_map)
    } else {
        load(url) {
            allowHardware(false)
            bitmapConfig(Bitmap.Config.ARGB_8888)
            val shimmer = Shimmer.ColorHighlightBuilder()
                .setBaseColor(Color.parseColor("#FF8A65"))
                .setHighlightColor(Color.parseColor("#E64A19"))
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setBaseAlpha(0.7f)
                .setHighlightAlpha(0.7f)
                .setDuration(1800)
                .build()
            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }
//            error(R.drawable.ic_default_video_3)
            placeholder(shimmerDrawable)
            transformations(transformation)
        }
    }
}

fun ImageView.load(url: String?) {
    if (url.isNullOrEmpty()) {
        load(R.drawable.ic_default_map)
    } else {
        load(url) {
            allowHardware(false)
            bitmapConfig(Bitmap.Config.ARGB_8888)
            val shimmer = Shimmer.ColorHighlightBuilder()
                .setBaseColor(Color.parseColor("#FF8A65"))
                .setHighlightColor(Color.parseColor("#E64A19"))
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setBaseAlpha(0.7f)
                .setHighlightAlpha(0.7f)
                .setDuration(1800)
                .build()
            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }
//            error(R.drawable.ic_default_video_3)
            placeholder(shimmerDrawable)

        }
    }
}

fun ImageView.load(
    @DrawableRes drawableId: Int,
    transformation: Transformation = RoundedCornersTransformation()
) {
    load(drawableId) {
        allowHardware(false)
        bitmapConfig(Bitmap.Config.ARGB_8888)
        transformations(transformation)
    }
}


fun ImageView.load(
    @DrawableRes drawableId: Int
) {
    load(drawableId) {
        crossfade(true)
        allowHardware(false)
        bitmapConfig(Bitmap.Config.ARGB_8888)
    }
}

fun ImageView.load(
    bitmap: Bitmap?,
    transformation: Transformation = RoundedCornersTransformation()
) {
    this.load(bitmap) {
        transformations(transformation)
    }
}

fun ImageView.load(
    uri: Uri?
) {
    this.load(uri)
}