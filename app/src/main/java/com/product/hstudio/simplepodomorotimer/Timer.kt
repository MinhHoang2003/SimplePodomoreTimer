package com.product.hstudio.simplepodomorotimer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Timer : View {

    private val backgroundCirclePaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = DEFAULT_BACKGROUND_CIRCLE_SIZE.toFloat()
        style = Paint.Style.STROKE
    }

    private val foregroundCirclePaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        strokeWidth = DEFAULT_BACKGROUND_CIRCLE_SIZE.toFloat()
    }

    private val textPaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        strokeWidth = DEFAULT_BACKGROUND_CIRCLE_SIZE.toFloat()
    }

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {

    }

    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(context, attr, defStyle) {

    }

    override fun onDraw(canvas: Canvas) {
        val cx = width / 2f
        val cy = height / 2f
        canvas.drawCircle(cx, cy, DEFAULT_CIRCLE_RADIUS.toFloat(), backgroundCirclePaint)
    }

    private companion object {
        const val DEFAULT_BACKGROUND_CIRCLE_SIZE = 10
        const val DEFAULT_CIRCLE_RADIUS = 100
        const val DEFAULT_BACKGROUND_CIRCLE_COLOR = Color.BLACK
        const val DEFAULT_FOREGROUND_CIRCLE_COLOR = Color.CYAN
        const val DEFAULT_TEXT_COLOR = Color.BLACK
    }

}