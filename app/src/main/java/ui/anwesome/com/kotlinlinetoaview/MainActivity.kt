package ui.anwesome.com.kotlinlinetoaview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import ui.anwesome.com.linetoaview.LineToAView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LineToAView.create(this)
        fullScreen()
    }
}

fun MainActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}