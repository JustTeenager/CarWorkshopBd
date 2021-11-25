package com.example.carworkshopbd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carworkshopbd.holders.EntityHolder
import android.widget.Filter;

class EntitiesAdapter(
    private val onItemClick: (Long) -> Unit
) : ListAdapter<ShowableInList, EntityHolder>(
    ShowableInListCallback()
) {

    val filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<ShowableInList>()

            if (constraint.isNullOrEmpty()) {
                return FilterResults().apply { values = currentList.toList() }
            }
            else {
                filteredList.addAll(currentList.filter {
                    it.isFoundByQuery(query = constraint.toString())
                })
            }
            return FilterResults().apply { values = filteredList.toList() }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            setData(results?.values as List<ShowableInList>)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityHolder {
        return EntityHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_entity, parent, false)
        ).apply {
            itemView.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION)
                    onItemClick(getItem(absoluteAdapterPosition).getUniqueId() ?: 0)
            }
        }
    }

    override fun onBindViewHolder(holder: EntityHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    fun setData(list: List<ShowableInList>) {
        submitList(list)
    }

    class ShowableInListCallback : DiffUtil.ItemCallback<ShowableInList>() {
        override fun areItemsTheSame(oldItem: ShowableInList, newItem: ShowableInList) =
            oldItem.getUniqueId() == newItem.getUniqueId()

        override fun areContentsTheSame(oldItem: ShowableInList, newItem: ShowableInList) =
            oldItem.equals(newItem)
    }

}

