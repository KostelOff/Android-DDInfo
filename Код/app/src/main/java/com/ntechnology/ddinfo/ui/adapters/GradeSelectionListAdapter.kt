package com.ntechnology.ddinfo.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ntechnology.ddinfo.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_selection.*
import kotlinx.android.synthetic.main.layout_selection_header.*

class GradeSelectionListAdapter(private val id: String) :
    RecyclerView.Adapter<GradeSelectionListAdapter.SelectionViewHolder>() {
    var items = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var headerTitle = ""
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var clickListener: ((String) -> Unit)? = null

    override fun getItemCount() = items.size + getHeaderCount()

    override fun getItemViewType(position: Int): Int {
        return if (isHeaderPosition(position)) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_SELECTION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SelectionViewHolder(
        LayoutInflater.from(parent.context).inflate(
            if (viewType == VIEW_TYPE_HEADER) {
                R.layout.layout_selection_header
            } else {
                R.layout.layout_selection
            }, parent, false))

    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
        if (isHeaderPosition(position)) {
            holder.bindHeader(headerTitle)
        } else {
            holder.bind(items[position - getHeaderCount()])
        }
    }

    private fun getHeaderCount(): Int {
        return if (headerTitle.isNotBlank()) 1 else 0
    }

    private fun isHeaderPosition(position: Int): Boolean {
        return position < getHeaderCount()
    }

    inner class SelectionViewHolder(private val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view

        fun bind(item: String) {

            titleTextView.text = item


            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    clickListener?.invoke(items[adapterPosition - getHeaderCount()])
                }
            }
        }

        fun bindHeader(header: String) {
            headerTitleTextView.text = header
        }
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_SELECTION = 1
    }
}