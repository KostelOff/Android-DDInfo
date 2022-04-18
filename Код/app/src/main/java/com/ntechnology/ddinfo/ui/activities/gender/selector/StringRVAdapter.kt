package com.ntechnology.ddinfo.ui.activities.gender.selector

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ntechnology.ddinfo.R
import kotlinx.android.synthetic.main.layout_selection.view.*

class StringRVAdapter(val items: Array<String>, val listener: ItemSelectedListener) :
    RecyclerView.Adapter<StringRVAdapter.StringVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): StringVH {
        return StringVH(LayoutInflater.from(parent.context).inflate(R.layout.layout_selection, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: StringVH, position: Int) {
        viewHolder.itemView.titleTextView.text = items[position]
        viewHolder.itemView.setOnClickListener { listener.itemSelected(position) }
    }


    class StringVH(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface ItemSelectedListener {
        fun itemSelected(position: Int)
    }
}