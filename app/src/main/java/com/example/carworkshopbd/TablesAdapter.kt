package com.example.carworkshopbd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carworkshopbd.holders.TablesHolder
import com.example.carworkshopbd.main.Table

class TablesAdapter(
    val onItemClick: (Table) -> Unit
) : ListAdapter<Table, TablesHolder>(
    TablesCallback(),
) {

    class TablesCallback : DiffUtil.ItemCallback<Table>() {
        override fun areItemsTheSame(oldItem: Table, newItem: Table): Boolean {
            return oldItem.value == newItem.value
        }

        override fun areContentsTheSame(oldItem: Table, newItem: Table): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablesHolder {
        return TablesHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_table, parent, false)
        ).apply {
            itemView.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION)
                    onItemClick(getItem(absoluteAdapterPosition))
            }
        }
    }

    override fun onBindViewHolder(holder: TablesHolder, position: Int) {
        holder.onBind(getItem(position).value)
    }

    fun setData(list: List<Table>) {
        submitList(list)
    }
}