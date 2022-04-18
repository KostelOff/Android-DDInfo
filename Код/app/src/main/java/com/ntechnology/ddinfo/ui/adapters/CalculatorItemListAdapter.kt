package com.ntechnology.ddinfo.ui.adapters

import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.models.CalculatorItemModel
import com.ntechnology.ddinfo.models.CalculatorItemViewModel
import com.ntechnology.ddinfo.models.SelectionModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_calculator_item.*

class CalculatorItemListAdapter :
    RecyclerView.Adapter<CalculatorItemListAdapter.CalculatorItemViewHolder>() {
    var items = listOf<CalculatorItemViewModel>()
        set(value) {
            DiffUtil.calculateDiff(CalculatorItemsDiffUtilCallback(value, field))
                .dispatchUpdatesTo(this)
            field = value
        }

    var clickListener: ((CalculatorItemModel, SelectionModel?) -> Unit)? = null

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CalculatorItemViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.layout_calculator_item, parent, false)).apply { initRV() }

    override fun onBindViewHolder(holder: CalculatorItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    var addEnDostig = false

    inner class CalculatorItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view),
        LayoutContainer {
        override val containerView: View?
            get() = view

        fun initRV() {
            selectionRecyclerView.layoutManager = LinearLayoutManager(view.context)
            selectionRecyclerView.adapter = ChosenSelectionAdapter()
        }

        fun bind(viewItem: CalculatorItemViewModel) {
            titleTextView.text = viewItem.item.name

            (selectionRecyclerView.adapter as? ChosenSelectionAdapter)?.let { adapter ->
                adapter.items = viewItem.selections

                var hasFooter = viewItem.selections.size < viewItem.item.maxSelections

                if (viewItem.item.id == "ovd") {
                    viewItem.selections.firstOrNull()?.let {
                        addEnDostig = when (it.title) {
                            "1 тарифный разряд", "2 тарифный разряд", "3 тарифный разряд", "4 тарифный разряд" -> {
                                true
                            }
                            else -> {
                                false
                            }
                        }
                    }
                }
                if (viewItem.item.id == "en_dostig") {
                    hasFooter =
                        viewItem.selections.size < (viewItem.item.maxSelections + if (addEnDostig) 1 else 0)
                }
                adapter.hasFooter = hasFooter
                adapter.itemClickListener = { selection ->
                    clickListener?.invoke(
                        this@CalculatorItemListAdapter.items[adapterPosition].item, selection)
                }
                adapter.footerClickListener = {
                    clickListener?.invoke(
                        this@CalculatorItemListAdapter.items[adapterPosition].item, null)
                }
            }
        }
    }

    class CalculatorItemsDiffUtilCallback(
        private val newItems: List<CalculatorItemViewModel>,
        private val oldItems: List<CalculatorItemViewModel>) : DiffUtil.Callback() {

        override fun getOldListSize() = oldItems.size
        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newItems[newItemPosition].item.name == oldItems[oldItemPosition].item.name
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newItems[newItemPosition] == oldItems[oldItemPosition]
        }
    }
}