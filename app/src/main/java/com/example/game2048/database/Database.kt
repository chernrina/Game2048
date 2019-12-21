package com.example.game2048.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DataEntry::class], version = 2)
abstract class Database: RoomDatabase() {
    abstract fun dateDAO(): DataDAO
}