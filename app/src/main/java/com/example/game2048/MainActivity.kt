package com.example.game2048

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var butChoiceRight: ImageView
    private lateinit var butChoiceLeft: ImageView
    private val listener = ClickListener()
    lateinit var sizeOfState: TextView
    val ROTATE_RIGHT = 1
    val ROTATE_LEFT = -1
    val SIZE_STATE = "count"
    var typeOfState = 4
    private val sizeInText = arrayOf("3x3", "4x4", "5x5", "6x6")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        sizeOfState = findViewById(R.id.sizeOfState)
        butChoiceRight = findViewById(R.id.go_right)
        butChoiceLeft = findViewById(R.id.go_left)
        butChoiceRight.setOnClickListener(listener)
        butChoiceLeft.setOnClickListener(listener)
    }


    inner class ClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            when (view.id) {
                R.id.go_right -> rotate(ROTATE_RIGHT)
                R.id.go_left -> rotate(ROTATE_LEFT)
            }
        }
    }

    fun rotate(side: Int) {
        var ind = sizeInText.indexOf(sizeOfState.text)
        if (ind == 0) ind = sizeInText.size
        when (side) {
            ROTATE_RIGHT -> {
                sizeOfState.text = sizeInText[(ind + 1) % sizeInText.size]
            }
            else -> {
                sizeOfState.text = sizeInText[(ind - 1) % sizeInText.size]
            }
        }
        typeOfState = sizeInText.indexOf(sizeOfState.text) +3
    }

    fun startGame(view: View) {
        startActivity(Intent(this, ActivityGame::class.java).putExtra(SIZE_STATE, typeOfState))
    }

    fun getDatabase(view: View) {
        startActivity(Intent(this, DatabaseActivity::class.java))
    }
}
