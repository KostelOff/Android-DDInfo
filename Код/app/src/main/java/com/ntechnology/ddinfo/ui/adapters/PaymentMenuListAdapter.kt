package com.ntechnology.ddinfo.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.models.PaymentMenuItemModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_payment_menu_item.*

class PaymentMenuListAdapter : RecyclerView.Adapter<PaymentMenuListAdapter.PaymentMenuItemViewHolder>() {
    var items = listOf<PaymentMenuItemModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var clickListener: ((PaymentMenuItemModel) -> Unit)? = null

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PaymentMenuItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_payment_menu_item, parent, false))

    override fun onBindViewHolder(holder: PaymentMenuItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class PaymentMenuItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view

        fun bind(item: PaymentMenuItemModel) {
            titleTextView.text = item.title

            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    clickListener?.invoke(items[adapterPosition])
                }
            }
        }
    }
}