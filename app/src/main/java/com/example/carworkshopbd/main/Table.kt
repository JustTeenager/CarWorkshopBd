package com.example.carworkshopbd.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Table(val value: String) : Parcelable {
    Automobile("Автомобили"),
    CarWorkshop("Мастерские"),
    Client("Клиенты"),
    DiscountCard("Скидочные карты"),
    Manager("Менеджеры"),
    Mechanic("Механики"),
    Problem("Проблемы"),
    None("Пустота")
}