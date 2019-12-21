package com.example.game2048

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.game2048.database.DataDAO
import com.example.game2048.database.DataEntry
import com.example.game2048.database.Database
import kotlinx.android.synthetic.main.activity_database.*
import kotlinx.android.synthetic.main.activity_state.*
import kotlinx.coroutines.*

class DatabaseActivity : AppCompatActivity() {


    lateinit var viewManager: LinearLayoutManager
    lateinit var viewAdapter: AdapterForDatabase
    lateinit var myItemDecoration: DividerItemDecoration
    private val listOfEntry by lazy {
        (application as App).getInstance().getDatabase().dateDAO().getAll()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        viewManager = LinearLayoutManager(this)
        viewAdapter = AdapterForDatabase()

        myItemDecoration = DividerItemDecoration(
            listOfPlayers.context,
            viewManager.orientation
        )
        listOfPlayers.addItemDecoration(myItemDecoration)

        listOfPlayers.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        listOfEntry.observe(this, Observer {
            viewAdapter.updateData(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_database, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteAll -> {
                GlobalScope.launch {
                    (application as App).getInstance().getDatabase().dateDAO().deleteAll()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}