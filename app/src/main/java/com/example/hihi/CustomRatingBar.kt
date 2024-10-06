package com.example.hihi

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat

class CustomRatingBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var rating: Float = 0f
    private var starCount: Int = 5
    private var starSize: Float = 100f
    private var selectedColor: Int = ContextCompat.getColor(context, R.color.colorStarSelected)
    private var unselectedColor: Int = ContextCompat.getColor(context, R.color.colorStarUnselected)

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    init {
        // Load custom attributes
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomRatingBar,
            0, 0
        ).apply {
            try {
                starCount = getInteger(R.styleable.CustomRatingBar_starCount, 5)
                starSize = getDimension(R.styleable.CustomRatingBar_starSize, 100f)
                selectedColor = getColor(R.styleable.CustomRatingBar_starSelectedColor, selectedColor)
                unselectedColor = getColor(R.styleable.CustomRatingBar_starUnselectedColor, unselectedColor)
            } finally {
                recycle()
            }
        }
    }

    fun setRating(rating: Float) {
        this.rating = rating.coerceIn(0f, starCount.toFloat())
        invalidate() // Request a redraw
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawStars(canvas)
    }

    private fun drawStars(canvas: Canvas) {
        for (i in 0 until starCount) {
            val x = (i * starSize) + (starSize / 2)
            val color = if (i < rating) selectedColor else unselectedColor
            paint.color = color
            canvas.drawCircle(x, starSize / 2, starSize / 2, paint) // Vẽ hình tròn
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
            val newRating = (event.x / starSize).toInt().coerceIn(0, starCount)
            setRating(newRating.toFloat())
            return true
        }
        return super.onTouchEvent(event)
    }
}