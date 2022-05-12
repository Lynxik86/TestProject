package com.example.android_dev.ui.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_dev.R
import com.example.android_dev.data.model.JokeResult


class RecyclerJokeAdapter(private val jokes: List<JokeResult>, private val itemClickListener: ItemClickListener):
    RecyclerView.Adapter<RecyclerJokeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_jokes, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.item_text)
    }

    override fun getItemCount(): Int {
        Log.d("Adapter Size ", jokes.size.toString())
        return jokes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //   h.bindItems(chuck.value!!?.get(position))

        holder.textView.text = jokes[position].joke
        //holder.itemView.setSelected(selectedPos == position)
        val item = jokes[position].joke
        Log.i("Joke position ", jokes[position].toString())
        holder.itemView.setOnClickListener{
            itemClickListener.onItemClickListener(item)}
    }
}
