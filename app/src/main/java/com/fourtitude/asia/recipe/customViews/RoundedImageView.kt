package com.fourtitude.asia.recipe.customViews

import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.opengl.ETC1.getWidth
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build


class RoundedImageView : ImageView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
    }

    override fun setImageDrawable(drawable: Drawable?) {
        val radius = 0.1f
        val bitmap = (drawable as BitmapDrawable).bitmap
        val rid = RoundedBitmapDrawableFactory.create(resources, bitmap)
        rid.cornerRadius = bitmap.width * radius
        super.setImageDrawable(rid)
    }
}