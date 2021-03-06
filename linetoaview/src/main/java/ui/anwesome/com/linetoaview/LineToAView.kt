package ui.anwesome.com.linetoaview

/**
 * Created by anweshmishra on 27/04/18.
 */

import android.app.Activity
import android.content.*
import android.view.*
import android.graphics.*

class LineToAView (ctx : Context) : View(ctx) {

    private val renderer : LTARenderer = LTARenderer(this)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }

    data class LTAState (var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0) {

        val scales : Array<Float> = arrayOf(0f, 0f, 0f)

        fun update(stopcb : (Float) -> Unit) {
            scales[j] += dir * 0.1f
            if (Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += dir.toInt()
                if (j == scales.size || j == -1) {
                    j -= dir.toInt()
                    dir = 0f
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class LTAAnimator(var view : View, var animated : Boolean = false) {

        fun animate(updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch (ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class LineToAShape(var i : Int, var state : LTAState = LTAState()) {

        fun draw(canvas : Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            val size : Float = Math.min(w, h)/3
            paint.strokeWidth = Math.min(w, h)/50
            paint.strokeCap = Paint.Cap.ROUND
            canvas.save()
            canvas.translate(w/2, h/2)
            canvas.drawLine(-size/2 * state.scales[0], 0f, size/2 * state.scales[0], 0f, paint)
            for (i in 0..1) {
                canvas.save()
                canvas.translate(0f, -size)
                canvas.rotate(30f * (1 - 2 * i) * state.scales[2])
                canvas.drawLine(0f, 0f, 0f, 2 * size * state.scales[1], paint)
                canvas.restore()
            }
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class LTARenderer(var view : LineToAView) {

        private val animator : LTAAnimator = LTAAnimator(view)

        private val lta : LineToAShape = LineToAShape(0)

        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            paint.color = Color.parseColor("#2980b9")
            lta.draw(canvas, paint)
            animator.animate {
                lta.update {
                    animator.stop()
                }
            }
        }

        fun handleTap() {
            lta.startUpdating {
                animator.start()
            }
        }
    }

    companion object {
        fun create(activity : Activity) : LineToAView {
            val view : LineToAView = LineToAView(activity)
            activity.setContentView(view)
            return view
        }
    }
}