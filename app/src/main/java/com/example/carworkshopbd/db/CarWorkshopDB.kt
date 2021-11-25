package com.example.carworkshopbd.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.carworkshopbd.db.dao.*
import com.example.carworkshopbd.db.entity.*

@Database(
    entities =
    [
        Automobile::class,
        CarWorkshop::class,
        Client::class,
        DiscountCard::class,
        Manager::class,
        Mechanic::class,
        Problem::class
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(CarDbConverter::class)
abstract class CarWorkshopDB : RoomDatabase() {

    abstract fun getAutomobileDao(): AutomobileDao
    abstract fun getCarWorkshopDao(): CarWorkshopDao
    abstract fun getClientDao(): ClientDao
    abstract fun getDiscountCardDao(): DiscountCardDao
    abstract fun getManagerDao(): ManagerDao
    abstract fun getMechanicDao(): MechanicDao
    abstract fun getProblemDao(): ProblemDao
}