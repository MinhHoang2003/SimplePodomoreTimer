package com.product.hstudio.simplepodomorotimer.ui.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import com.product.hstudio.simplepodomorotimer.R
import kotlin.math.min

class Timer : View {

    // Config
    @ColorInt
    private var backgroundColor: Int = Color.BLACK
    @ColorInt
    private var foregroundColor: Int = Color.CYAN
    private var circleSize: Float = DEFAULT_BACKGROUND_CIRCLE_SIZE.toFloat()
    private var textSize: Float  = DEFAULT_BACKGROUND_CIRCLE_SIZE.toFloat()
    private val textBoundRect = Rect()
    private val foregroundRect = RectF()
    private var circleRadius: Float = DEFAULT_CIRCLE_RADIUS.toFloat()
    private var time: Int = 0

    // Property
    private var progress: Float = 0.0F
    private var animator: ValueAnimator? = null
    private var currentTime: String = "00:00"

    private val backgroundCirclePaint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
    }
    private val foregroundCirclePaint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint: Paint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        textAlign = Paint.Align.CENTER
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init(context, attr)
    }

    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(
        context,
        attr,
        defStyle
    ) {
        init(context, attr)
    }

    private fun init(context: Context, attr: AttributeSet?) {
        val arr = context.obtainStyledAttributes(attr, R.styleable.Timer)
        textSize = arr.getDimension(R.styleable.Timer_textSize, DEFAULT_BACKGROUND_CIRCLE_SIZE.toFloat())
        foregroundColor = arr.getColor(R.styleable.Timer_foregroundCircleColor, Color.CYAN)
        backgroundColor = arr.getColor(R.styleable.Timer_backgroundCircleColor, Color.BLACK)
        circleRadius = arr.getDimension(R.styleable.Timer_circleRadius, DEFAULT_CIRCLE_RADIUS.toFloat())
        circleSize = arr.getDimension(R.styleable.Timer_size, DEFAULT_BACKGROUND_CIRCLE_SIZE.toFloat())
        time = arr.getInt(R.styleable.Timer_time, 0)
        arr.recycle()

        // Apply resources
        backgroundCirclePaint.apply {
            strokeWidth = circleSize
            color = backgroundColor
        }

        foregroundCirclePaint.apply {
            strokeWidth = circleSize
            color = foregroundColor
        }
        textPaint.textSize = textSize
    }
    override fun onDraw(canvas: Canvas) {
        Log.d(TAG, "onDraw: width = $width, height = $height")
        val cx = width / 2f
        val cy = height / 2f
        // draw background circle
        canvas.drawCircle(cx, cy, circleRadius,  backgroundCirclePaint)

        // draw foreground circle
        foregroundRect.set(
            cx - circleRadius,
            cy - circleRadius,
            cx + circleRadius,
            cy + circleRadius
        )
        canvas.drawArc(foregroundRect, -90F, 360 * progress, false, foregroundCirclePaint)

        // draw text
        textPaint.getTextBounds(currentTime, 0, currentTime.length, textBoundRect)
        canvas.drawText(currentTime, cx, cy + textBoundRect.height() /2, textPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)

        val heightSizeSpec = MeasureSpec.getSize(heightMeasureSpec)
        val widthSizeSpec = MeasureSpec.getSize(widthMeasureSpec)
        
        Log.d(TAG, "onMeasure: height spec: ${MeasureSpec.toString(heightMeasureSpec)}")
        Log.d(TAG, "onMeasure: width spec: ${MeasureSpec.toString(widthMeasureSpec)}")
        val size = min(heightSizeSpec, widthSizeSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
    fun start(time: Long) {
        when {
            animator?.isPaused == true -> {
                animator?.resume()
                return
            }

            animator?.isRunning == true -> {
                animator?.pause()
                return
            }
        }

        animator = ValueAnimator.ofFloat(0F, 1F).apply {
            interpolator = LinearInterpolator()
            duration = time

            addUpdateListener {
                progress = it.animatedValue as Float
                currentTime = (progress * (time / 1000)).toInt().formatToText()
                invalidate()
            }
            start()
        }
    }

    fun reset() {
        animator?.cancel()
        currentTime = 0.formatToText()
        progress = 0f
        invalidate()
    }
    @SuppressLint("DefaultLocale")
    private fun Int.formatToText(): String {
        val minute = this / 60
        val second = this % 60
        return String.format("%02d:%02d", minute, second)
    }

    fun stop() {
        animator?.cancel()
    }

    fun pause() {
        animator?.pause()
    }

    private companion object {
        private const val TAG = "Timer"
        const val DEFAULT_BACKGROUND_CIRCLE_SIZE = 10
        const val DEFAULT_CIRCLE_RADIUS = 100
        const val DEFAULT_BACKGROUND_CIRCLE_COLOR = Color.BLACK
        const val DEFAULT_FOREGROUND_CIRCLE_COLOR = Color.CYAN
        const val DEFAULT_TEXT_COLOR = Color.BLACK
    }

}