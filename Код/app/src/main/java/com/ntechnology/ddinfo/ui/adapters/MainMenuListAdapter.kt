package com.ntechnology.ddinfo.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.models.MenuItemModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_main_menu_item.*

class MainMenuListAdapter : RecyclerView.Adapter<MainMenuListAdapter.MainMenuItemViewHolder>() {
    var items = listOf<MenuItemModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var clickListener: ((MenuItemModel) -> Unit)? = null

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainMenuItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_main_menu_item, parent, false))

    override fun onBindViewHolder(holder: MainMenuItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MainMenuItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view

        fun bind(item: MenuItemModel) {
            titleTextView.text = item.title
            descriptionTextView.text = item.description

            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    clickListener?.invoke(items[adapterPosition])
                }
            }
        }
    }
}