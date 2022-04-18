package com.example.android_dev

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_dev.R.id.item_jokes_text
import com.example.android_dev.model.ChuckResult


class RecyclerChuckAdapter(private val chuck: List<ChuckResult>) :
    RecyclerView.Adapter<RecyclerChuckAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_jokes, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(item_jokes_text)
    }

    override fun getItemCount(): Int {
        Log.d("Adapter Size ", chuck.size.toString())
        return chuck.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //   h.bindItems(chuck.value!!?.get(position))
        holder.textView.text = chuck[position].id
        //    holder.textView.text = chuck[position].id
    }
}
