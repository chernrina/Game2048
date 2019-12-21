package com.example.game2048

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.game2048.database.DataDAO
import com.example.game2048.database.DataEntry
import com.example.game2048.game.GameField
import com.example.game2048.game.GameLogic
import kotlinx.android.synthetic.main.activity_state.*
import kotlinx.android.synthetic.main.save_game.view.*
import kotlinx.coroutines.*


class ActivityGame : AppCompatActivity() {

    private val SIZE_STATE = "count"
    private val SAVED_FIELD = "field"
    private val ID_PLAYER = "player"
    var listener = TouchListener()
    var nums = emptyArray<Int>()
    lateinit var game : GameLogic
    lateinit var field : GameField
    lateinit var viewManager : GridLayoutManager
    lateinit var viewAdapter: MyAdapter
    lateinit var prefs: SharedPreferences
    lateinit var dialogView : View
    private lateinit var database : DataDAO
    var sizeOfState = 4
    var idPlayer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sizeOfState = intent.extras!!.getInt(SIZE_STATE)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_state)
        prefs = getSharedPreferences(SAVED_FIELD, Context.MODE_PRIVATE)

        field = GameField(sizeOfState)
        field.generateNewCell(2)
        updateField()
        game = GameLogic(field)

        database = (application as App).getInstance().getDatabase().dateDAO()
        dialogView = LayoutInflater.from(this).inflate(R.layout.save_game, null)

        sizeOfState = intent.extras!!.getInt("count")
        viewManager = GridLayoutManager(this, sizeOfState)
        viewAdapter = MyAdapter(nums)

        state.setOnTouchListener(listener)
        recycler.setOnTouchListener(listener)
        recycler.apply {
            setHasFixedSize(true)
            layoutManager= viewManager as RecyclerView.LayoutManager?
            adapter = viewAdapter
        }
    }

    private var recordInt = 0
    override fun onStart() {
        super.onStart()
        recordInt = prefs.getInt(SAVED_FIELD,0)
        idPlayer = prefs.getInt(ID_PLAYER,0)
        record.text = recordInt.toString()
    }

    override fun onStop() {
        super.onStop()
        if (game.getScore() > recordInt) prefs.edit().putInt(SAVED_FIELD, game.getScore()).apply()
        prefs.edit().putInt(ID_PLAYER,idPlayer).apply()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveGame -> {
                saveGame()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateField() {
        var i = 0
        while (i < sizeOfState) {
            nums += field.getLine(i)
            i++
        }
    }

    private fun saveGame() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.yourName))
        builder.setView(dialogView)
        builder.setPositiveButton(getString(R.string.save)) { _, _ ->
            val name = dialogView.nameOfPlayer.text.toString()
            GlobalScope.launch {
                database.insert(DataEntry(idPlayer,name,game.getScore()))
            }
            idPlayer++
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_LONG).show()
        }
        val dialog = builder.create()
        dialog.show()
    }

    var x_prev : Float = 0.0f
    var y_prev: Float = 0.0f
    var win = false

    inner class TouchListener : View.OnTouchListener {
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            val x = event.x
            val y = event.y
            when(event.action) {
                ACTION_DOWN-> {
                    x_prev = x
                    y_prev = y
                }
                ACTION_UP -> {
                    win = game.updateState(getMove(x,y))
                    if (nums.indexOf(0) != -1) {
                        field.generateNewCell(1)
                    }
                    nums = emptyArray()
                    updateField()
                    viewAdapter= MyAdapter(nums)

                    recycler.apply {
                        setHasFixedSize(true)
                        layoutManager= viewManager as RecyclerView.LayoutManager?
                        adapter = viewAdapter
                    }
                    score.text = game.getScore().toString()
                    if (win) {
                        win()
                    }
                    checkEmptyCell()
                }
            }
            return true
        }
    }

    private fun win() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.win))
        builder.setPositiveButton(R.string.save) { _, _ ->
            saveGame()
        }
        builder.setNegativeButton(getString(R.string.continueGame)) { _, _ ->
            win=false
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun checkEmptyCell() {
        if (nums.indexOf(0) == -1) {
            if (!game.checkMove()) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(getString(R.string.gameOver))
                builder.setPositiveButton(R.string.save) { _, _ ->
                    saveGame()
                }
                builder.setNegativeButton(getString(R.string.exit)) { _, _ ->
                    finish()
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
    }

    private val RIGHT = 1
    private val LEFT = 2
    private val UP =3
    private val DOWN = 4
    fun getMove(x:Float,y:Float): Int {
        return when {
            (x - x_prev) > 40 -> RIGHT
            (x_prev - x) > 40 -> LEFT
            (y_prev-y) >40 -> UP
            else -> DOWN
        }
    }
}