package com.example.game2048.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score_table")
data class DataEntry (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var playerName: String = "",
    var score: Int = 0
)