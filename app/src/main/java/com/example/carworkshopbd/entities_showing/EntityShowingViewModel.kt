package com.example.carworkshopbd.entities_showing

import androidx.lifecycle.ViewModel
import com.example.carworkshopbd.ShowableInList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class EntityShowingViewModel : ViewModel() {
    protected val _dataFlow = MutableSharedFlow<List<ShowableInList>>()
    val dataFlow = _dataFlow.asSharedFlow()

    abstract fun loadData()

    abstract fun deleteItem(item: ShowableInList?)
}