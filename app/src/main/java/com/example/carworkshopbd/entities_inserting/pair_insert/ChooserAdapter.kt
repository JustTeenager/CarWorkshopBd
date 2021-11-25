package com.example.carworkshopbd.entities_inserting.pair_insert

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carworkshopbd.R
import com.example.carworkshopbd.ShowableInList
import com.example.carworkshopbd.holders.EntityHolder
import com.example.carworkshopbd.utils.visible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChooserAdapter(
    private val maxChosen: Int = 1,
    private val scope: CoroutineScope
) : ListAdapter<ShowableInList, EntityHolder>(
    ShowableInListCallback()
) {

    private val idsList: MutableList<Long> = mutableListOf()

    private val flow = MutableSharedFlow<MutableList<Int>>(replay = 1)
    private var prevCheckedItemIndex = -2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityHolder {
        return EntityHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_entity,
                parent,
                false
            )
        ).apply {
            binding.chbSelected.visible()
            scope.launch {
                flow.collectLatest {
                    binding.chbSelected.isChecked = it.contains(absoluteAdapterPosition)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: EntityHolder, position: Int) {
        holder.apply {
            onBind(getItem(position))
            if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                binding.chbSelected.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        prevCheckedItemIndex = absoluteAdapterPosition
                        flow.tryEmit(flowListWithDiff { it.add(absoluteAdapterPosition) })
                        idsList.add(item?.getUniqueId() ?: 0)
                        if (idsList.size > maxChosen) {
                            idsList.removeAll { it != item?.getUniqueId() }
                            flow.tryEmit(
                                flowListWithDiff {
                                    it.removeAll { index -> index != absoluteAdapterPosition }
                                }
                            )
                        }

                    } else {
                        if (prevCheckedItemIndex == position)
                            idsList.remove(item?.getUniqueId())
                        flow.tryEmit(flowListWithDiff { it.remove(element = absoluteAdapterPosition) })
                    }
                }
            }
        }
    }

    fun getChosenIds() = idsList.toList()

    fun setData(list: List<ShowableInList>) {
        submitList(list)
    }

    private fun flowListWithDiff(diff: (MutableList<Int>) -> Unit): MutableList<Int> {
        return (flow.replayCache.firstOrNull() ?: mutableListOf())
            .apply { diff(this) }
            .toMutableList()
    }

    class ShowableInListCallback : DiffUtil.ItemCallback<ShowableInList>() {
        override fun areItemsTheSame(oldItem: ShowableInList, newItem: ShowableInList) =
            oldItem.getUniqueId() == newItem.getUniqueId()

        override fun areContentsTheSame(oldItem: ShowableInList, newItem: ShowableInList) =
            oldItem.equals(newItem)
    }
}