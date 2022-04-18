package com.ntechnology.ddinfo.ui.adapters

import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.models.GradesSelectionViewItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_calculator_item.*

class GradeItemsListAdapter : RecyclerView.Adapter<GradeItemsListAdapter.GradeItemsViewHolder>() {

    var items = listOf<GradesSelectionViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    var clickListener: ((GradesSelectionViewItem, String?) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GradeItemsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_calculator_item, parent, false)).apply { initRV() }


    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: GradeItemsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class GradeItemsViewHolder(private val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view

        fun initRV() {
            selectionRecyclerView.layoutManager = LinearLayoutManager(view.context)
            selectionRecyclerView.adapter = GradesSelectionAdapter()
        }

        fun bind(viewItem: GradesSelectionViewItem) {
            titleTextView.text = viewItem.item.title

            (selectionRecyclerView.adapter as? GradesSelectionAdapter)?.let { adapter ->
                adapter.items = viewItem.selections
                adapter.hasFooter = viewItem.selections.isEmpty()
                adapter.itemClickListener = { selection ->
                    clickListener?.invoke(this@GradeItemsListAdapter.items[adapterPosition], selection)
                }
                adapter.footerClickListener = {
                    clickListener?.invoke(this@GradeItemsListAdapter.items[adapterPosition], null)
                }
            }
        }
    }
}