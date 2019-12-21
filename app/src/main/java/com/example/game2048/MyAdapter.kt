package com.example.game2048

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val sizeOfState: Array<Int>) :  RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var num = view.findViewById<TextView>(R.id.cell)
        fun getNum(): TextView {
            return num
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.cell_item,parent, false)
        return MyViewHolder(view)
    }

    private val colors: Map<Int,Int> = mapOf(2 to R.color.two,4 to R.color.four, 8 to R.color.eight, 16 to R.color.sixteen,
        32 to R.color.thirty,64 to R.color.sixty,128 to R.color.hundred,256 to R.color.hundred,512 to R.color.hundred)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cell = sizeOfState[position]
        if (cell !=0) {
            holder.getNum().text = cell.toString()
            if (colors.containsKey(cell)){
                holder.getNum().setBackgroundColor(holder.getNum().context.resources.getColor(colors[cell] as Int))
            } else {
                holder.getNum().setBackgroundColor(holder.getNum().context.resources.getColor(R.color.thousand))
            }
        }
    }

    override fun getItemCount(): Int {
        return sizeOfState.size
    }
}