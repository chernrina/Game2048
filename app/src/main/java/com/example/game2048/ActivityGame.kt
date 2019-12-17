package com.example.game2048

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.game2048.game.GameField
import com.example.game2048.game.GameLogic
import kotlinx.android.synthetic.main.activity_state.*


class ActivityGame : AppCompatActivity() {

    var sizeOfState = 4
    val SIZE_STATE = "count"
    var listener = TouchListener()
    lateinit var game : GameLogic
    var nums = emptyArray<Int>()
    lateinit var field : GameField
    lateinit var viewManager : GridLayoutManager
    lateinit var viewAdapter: MyAdapter
    lateinit var myItemDecoration : DividerItemDecoration
    lateinit var prefs: SharedPreferences
    private val SAVED_FIELD = "field"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sizeOfState = intent.extras!!.getInt(SIZE_STATE)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_state)
        prefs = getSharedPreferences(SAVED_FIELD, Context.MODE_PRIVATE)
        state.setOnTouchListener(listener)
        field = GameField(sizeOfState)
        field.generateNewCell(2)
        game = GameLogic(field)

        updateField()
        sizeOfState = intent.extras!!.getInt("count")
        viewManager = GridLayoutManager(this, sizeOfState)
        viewAdapter = MyAdapter(nums)
        recycler.setOnTouchListener(listener)
        recycler.apply {
            setHasFixedSize(true)
            layoutManager= viewManager as RecyclerView.LayoutManager?
            adapter = viewAdapter
        }
        myItemDecoration = DividerItemDecoration(
            recycler.context,
            viewManager.orientation
        )
        recycler.addItemDecoration(myItemDecoration)

        score.text = game.getScore().toString()
    }

    var recordInt = 0
    override fun onStart() {
        super.onStart()
        recordInt = prefs.getInt(SAVED_FIELD,0)
        record.text = recordInt.toString()
    }

    override fun onStop() {
        super.onStop()
        if (game.getScore() > recordInt) prefs.edit().putInt(SAVED_FIELD, game.getScore()).apply()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun updateField() {
        var i = 0
        while (i < sizeOfState) {
            nums += field.getLine(i)
            i++
        }
    }

    private fun win() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Победа!")
        builder.setPositiveButton("Сохранить результат") { _, _ ->

        }
        builder.setNegativeButton("Играть дальше") { _, _ ->
            win=false
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun checkEmptyCell() {
        if (nums.indexOf(0) == -1) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Игра окончена")
            builder.setPositiveButton("Сохранить результат") { _, _ ->

            }
            builder.setNegativeButton("Начать заново") { _, _ ->
                ///новая игра или выйти

            }
            val dialog = builder.create()
            dialog.show()
        }
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
                    field.generateNewCell(1)
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
                    //if (!field.correctField()) {
                        ////Диалоговое окно игра окончена
                    //}

                }
            }
            return true
        }
    }

    val RIGHT = 1
    val LEFT = 2
    val UP =3
    val DOWN = 4
    fun getMove(x:Float,y:Float): Int {
        if((x - x_prev) > 40 ) return RIGHT
        else if((x_prev - x) > 40 ) return LEFT
        else if ((y_prev-y) >40 ) return UP
        else return DOWN
    }
}