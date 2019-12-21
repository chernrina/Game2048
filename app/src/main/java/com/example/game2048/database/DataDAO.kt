package com.example.game2048.database

import android.os.FileObserver.DELETE
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataDAO {

    @Insert
    fun insert(dataEntry: DataEntry)

    @Query("UPDATE score_table SET score = :score WHERE playerName = :name")
    fun updateScore(name: String, score: Int)

    @Query("SELECT * from score_table")
    fun getAll(): LiveData<List<DataEntry>>

    @Query("SELECT * FROM score_table WHERE playerName = :name")
    fun getByName(name: String): DataEntry

    @Query("DELETE from score_table")
    fun deleteAll()

}