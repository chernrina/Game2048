package com.example.game2048

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.game2048.database.DataEntry

class AdapterForDatabase :  RecyclerView.Adapter<AdapterForDatabase.MyViewHolder>(){

    private var listOfPlayers = listOf<DataEntry>()

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.name)
        private var score = view.findViewById<TextView>(R.id.score)
        fun getName(): TextView {
            return name
        }
        fun getScore(): TextView {
            return score
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.database_item,parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entry = listOfPlayers[position]
        holder.getName().text = entry.playerName
        holder.getScore().text = entry.score.toString()
    }

    override fun getItemCount(): Int {
        return listOfPlayers.size
    }

    fun updateData(list: List<DataEntry>) {
        listOfPlayers = list
        notifyDataSetChanged()
    }
}