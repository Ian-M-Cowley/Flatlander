package com.flatlander.flatlander.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView


/**
 * Created by iancowley on 8/28/17.
 */
class MatchWidthImageView : ImageView {
    companion object {
        val DEFAULT_EMPTY_ASPECT = 16f / 9f
    }

    private var mAspect = DEFAULT_EMPTY_ASPECT
    private var mIsEmptyAspectSpecified = false
    private var mIsSkipCurrentLayoutRequest: Boolean = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun requestLayout() {
        if (!mIsSkipCurrentLayoutRequest) super.requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)

        setMeasuredDimension(w, w)
    }

    fun setDefaultEmptyAspect() {
        mIsEmptyAspectSpecified = false
        setEmptyAspectInternal(DEFAULT_EMPTY_ASPECT)
    }

    fun setEmptyAspect(width: Int, height: Int) {
        mIsEmptyAspectSpecified = true
        setEmptyAspectInternal(width.toFloat() / height.toFloat())
    }

    fun setEmptyAspect(aspect: Float) {
        mIsEmptyAspectSpecified = true
        setEmptyAspectInternal(aspect)
    }

    private fun setEmptyAspectInternal(aspect: Float) {
        if (aspect <= 0) throw IllegalArgumentException("Aspect cannot be <= 0")
        mAspect = aspect
        requestLayout()
    }

    override fun setImageBitmap(bm: Bitmap?) {
        if (bm == null) {
            setImageDrawable(null)
        } else {
            val drawable = BitmapDrawable(resources, bm)
            bm.density = Bitmap.DENSITY_NONE
            setImageDrawable(drawable)
        }
    }

    override fun setImageDrawable(drawable: Drawable?) {
        mIsSkipCurrentLayoutRequest = mIsEmptyAspectSpecified
        super.setImageDrawable(drawable)
        mIsSkipCurrentLayoutRequest = false
    }

    override fun setImageResource(resId: Int) {
        setImageDrawable(resources.getDrawable(resId))
    }
}