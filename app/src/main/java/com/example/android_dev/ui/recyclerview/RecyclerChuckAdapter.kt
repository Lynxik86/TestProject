package com.example.android_dev.ui.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_dev.R
import com.example.android_dev.data.model.ChuckResult


class RecyclerChuckAdapter(private val chuck: List<ChuckResult>,  private val buttonClickListener: ButtonClickListener, private val itemClickListener: ItemClickListener):
    RecyclerView.Adapter<RecyclerChuckAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_jokes, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.item_text)
        val button: View = itemView.findViewById(R.id.buttonDelete)
    }

    override fun getItemCount(): Int {
        Log.d("Adapter Size ", chuck.size.toString())
        return chuck.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = chuck[position].id
        val item = chuck[position].id
        holder.button.setOnClickListener {
            buttonClickListener.onButtonClickListener(item)
        }
        holder.itemView.setOnClickListener{
            itemClickListener.onItemClickListener(item)}
   }

}
