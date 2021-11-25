package com.example.carworkshopbd.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.carworkshopbd.ShowableInList
import com.example.carworkshopbd.databinding.ItemEntityBinding

class EntityHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    val binding by viewBinding(ItemEntityBinding::bind)
    var item: ShowableInList? = null

    fun onBind(showableInList: ShowableInList) = with(binding) {
        item = showableInList
        tvDescription.text = showableInList.getDetails()
        tvTitle.text = showableInList.getTitle()
        tvNum.text = showableInList.getNum()
    }
}