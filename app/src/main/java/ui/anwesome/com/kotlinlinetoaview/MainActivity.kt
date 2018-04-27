package ui.anwesome.com.kotlinlinetoaview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.linetoaview.LineToAView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LineToAView.create(this)
    }
}
