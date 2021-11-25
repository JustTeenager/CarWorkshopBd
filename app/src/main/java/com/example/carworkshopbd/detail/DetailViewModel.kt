package com.example.carworkshopbd.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carworkshopbd.db.CarWorkshopDB
import com.example.carworkshopbd.main.Table
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    table: Table,
    private val id: Long?,
    private val db: CarWorkshopDB?
) : ViewModel() {

    val detailFlow: StateFlow<Any>
        get() = _detailFlow.asStateFlow()
    private val _detailFlow: MutableStateFlow<Any> = MutableStateFlow(Any())

    init {
        when (table) {
            Table.Automobile -> {
                getAuto()
            }
            Table.CarWorkshop -> {
                getCarWork()
            }
            Table.Client -> {
                getClient()
            }
            Table.DiscountCard -> {
                getDiscountCard()
            }
            Table.Mechanic -> {
                getMechanic()
            }
            Table.Problem -> {
                getProblem()
            }
            Table.Manager -> {
                getManager()
            }
            Table.None -> {

            }
        }
    }

    private fun getAuto() = viewModelScope.launch {
        db?.let {
            if (id != null)
                _detailFlow.value = db.getAutomobileDao().getAutoWithProblems(id)
        }

    }

    private fun getClient() = viewModelScope.launch {
        db?.let {
            if (id != null)
                _detailFlow.value = db.getClientDao().getClient(id)
        }
    }

    private fun getCarWork() = viewModelScope.launch {
        db?.let {
            if (id != null)
                _detailFlow.value = db.getCarWorkshopDao().getCarWorkshop(id)
        }
    }

    private fun getManager() = viewModelScope.launch {
        db?.let {
            if (id != null)
                _detailFlow.value = db.getManagerDao().getManager(id)
        }
    }

    private fun getMechanic() = viewModelScope.launch {
        db?.let {
            if (id != null)
                _detailFlow.value = db.getMechanicDao().getMechanic(id)
        }
    }

    private fun getDiscountCard() = viewModelScope.launch {
        db?.let {
            if (id != null)
                _detailFlow.value = db.getDiscountCardDao().getDiscountCard(id)
        }
    }

    private fun getProblem() = viewModelScope.launch {
        db?.let {
            if (id != null)
                _detailFlow.value = db.getProblemDao().getProblem(id)
        }
    }
}