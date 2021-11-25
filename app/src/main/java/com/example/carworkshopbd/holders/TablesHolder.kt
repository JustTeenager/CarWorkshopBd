package com.example.carworkshopbd.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.carworkshopbd.databinding.ItemTableBinding

class TablesHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding by viewBinding(ItemTableBinding::bind)
    fun onBind(string: String) {
        binding.tvTableName.text = string
    }
}