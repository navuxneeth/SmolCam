package com.example.smolcam

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import kotlin.math.roundToInt

class InteractiveSliderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var majorTicks = emptyList<String>()
    private var allTicks = emptyList<String>()

    private val scroller = OverScroller(context)
    private var scrollXOffset = 0f
    private var minScrollX = 0f
    private var maxScrollX = 0f
    private var lastX = 0f

    private val itemWidthDp = 40f
    private val itemSpacingDp = 4f
    private val itemWidthPx = dpToPx(itemWidthDp)
    private val itemSpacingPx = dpToPx(itemSpacingDp)
    private val totalItemWidthPx = itemWidthPx + itemSpacingPx

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#1E3C40") // Dark Teal
        textSize = dpToPx(12f)
        textAlign = Paint.Align.CENTER
    }

    private val majorTickPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#1E3C40") // Dark Teal
        strokeWidth = dpToPx(2f)
    }

    private val minorTickPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#1E3C40") // Dark Teal
    }

    private val centerLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#D95D4A") // Accent Red
        strokeWidth = dpToPx(2f)
    }

    private val dottedLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#801E3C40") // Dark Teal with alpha
        strokeWidth = dpToPx(1f)
        pathEffect = DashPathEffect(floatArrayOf(dpToPx(2f), dpToPx(6f)), 0f)
    }

    var onValueChanged: ((String) -> Unit)? = null

    fun setTicks(newTicks: List<String>) {
        majorTicks = newTicks
        allTicks = buildList {
            majorTicks.forEachIndexed { index, tick ->
                add(tick)
                if (index < majorTicks.size - 1) {
                    add("") // Placeholder for a minor tick
                }
            }
        }
        maxScrollX = (allTicks.size - 1) * totalItemWidthPx
        minScrollX = 0f
        scrollXOffset = 0f

        val centerIndex = findCenterIndex()
        val centerValue = allTicks.getOrNull(centerIndex)
        if (!centerValue.isNullOrEmpty()) {
            onValueChanged?.invoke(centerValue)
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val viewHeight = height.toFloat()
        val viewWidth = width.toFloat()
        val viewCenter = viewWidth / 2

        val dottedLineY = viewHeight / 2 + dpToPx(8f)
        canvas.drawLine(0f, dottedLineY, viewWidth, dottedLineY, dottedLinePaint)

        allTicks.forEachIndexed { index, text ->
            val itemCenterX = viewCenter + (index * totalItemWidthPx) - scrollXOffset
            if (itemCenterX > -itemWidthPx && itemCenterX < viewWidth + itemWidthPx) {
                if (text.isNotEmpty()) {
                    canvas.drawText(text, itemCenterX, viewHeight / 2 - dpToPx(4f), textPaint)
                    canvas.drawLine(itemCenterX, viewHeight / 2 + dpToPx(4f), itemCenterX, viewHeight / 2 + dpToPx(16f), majorTickPaint)
                } else {
                    canvas.drawCircle(itemCenterX, dottedLineY, dpToPx(1.5f), minorTickPaint)
                }
            }
        }

        canvas.drawLine(viewCenter, viewHeight / 2 + dpToPx(2f), viewCenter, viewHeight / 2 + dpToPx(22f), centerLinePaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                scroller.forceFinished(true)
                lastX = event.x
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - lastX
                lastX = event.x
                scrollXOffset -= dx
                scrollXOffset = scrollXOffset.coerceIn(minScrollX, maxScrollX)

                val centerIndex = findCenterIndex()
                val centerValue = allTicks.getOrNull(centerIndex)
                if (!centerValue.isNullOrEmpty()) {
                    onValueChanged?.invoke(centerValue)
                }

                invalidate()
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val centerIndex = findCenterIndex()
                val targetScrollX = centerIndex * totalItemWidthPx
                scroller.startScroll(scrollXOffset.toInt(), 0, (targetScrollX - scrollXOffset).toInt(), 0, 300)
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollXOffset = scroller.currX.toFloat()
            val centerIndex = findCenterIndex()
            val centerValue = allTicks.getOrNull(centerIndex)
            if (!centerValue.isNullOrEmpty()) {
                onValueChanged?.invoke(centerValue)
            }
            invalidate()
        }
    }

    private fun findCenterIndex(): Int {
        return (scrollXOffset / totalItemWidthPx).roundToInt().coerceIn(0, allTicks.size - 1)
    }

    private fun dpToPx(dp: Float): Float {
        return dp * resources.displayMetrics.density
    }
}