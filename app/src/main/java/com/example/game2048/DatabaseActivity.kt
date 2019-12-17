package com.example.game2048

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.game2048.database.DataDAO
import com.example.game2048.database.Database

class DatabaseActivity : AppCompatActivity() {

    lateinit var database : DataDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

    }

}