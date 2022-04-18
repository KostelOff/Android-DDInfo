package com.ntechnology.ddinfo.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ntechnology.ddinfo.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_chosen_selection.*

class GradesSelectionAdapter : RecyclerView.Adapter<GradesSelectionAdapter.SelectionItemViewHolder>() {
    var items = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var hasFooter = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: ((String) -> Unit)? = null
    var footerClickListener: (() -> Unit)? = null

    override fun getItemCount() = items.size + getFooterCount()

    override fun getItemViewType(position: Int): Int {
        return if (position < items.size) {
            VIEW_TYPE_ITEM
        } else {
            VIEW_TYPE_FOOTER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SelectionItemViewHolder(
        LayoutInflater.from(parent.context).inflate(
            if (viewType == VIEW_TYPE_ITEM) {
                R.layout.layout_chosen_selection
            } else {
                R.layout.layout_choose_selection_footer
            }, parent, false))

    override fun onBindViewHolder(holder: SelectionItemViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            holder.bind(items[position])
        } else {
            holder.bindFooter()
        }
    }

    private fun getFooterCount(): Int {
        return if (hasFooter) 1 else 0
    }

    inner class SelectionItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view

        fun bind(item: String) {
            chosenSelectionTextView.text = item

            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    itemClickListener?.invoke(items[adapterPosition])
                }
            }
        }

        fun bindFooter() {
            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    footerClickListener?.invoke()
                }
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_FOOTER = 1
    }
}