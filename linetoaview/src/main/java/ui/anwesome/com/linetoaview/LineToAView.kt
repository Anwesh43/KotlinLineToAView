package ui.anwesome.com.linetoaview

/**
 * Created by anweshmishra on 27/04/18.
 */

import android.content.*
import android.view.*
import android.graphics.*

class LineToAView (ctx : Context) : View(ctx) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}