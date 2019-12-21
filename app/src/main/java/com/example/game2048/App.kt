package com.example.game2048

import android.app.Application
import androidx.room.Room
import com.example.game2048.database.Database


class App : Application() {

    private lateinit var instance: App
    private lateinit var database: Database


    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, Database::class.java, "database").fallbackToDestructiveMigration().build()
    }


    fun getInstance(): App {
        return instance
    }


    fun getDatabase(): Database {
        return database
    }
}