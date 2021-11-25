package com.example.carworkshopbd.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.carworkshopbd.db.entity.CarWorkshop
import com.example.carworkshopbd.db.entity.releation.CarWorkshopWithAll

@Dao
interface CarWorkshopDao {

    @Insert
    suspend fun insertCarWorkshop(carWorkshop: CarWorkshop)

    @Delete
    suspend fun deleteCarWorkshop(carWorkshop: CarWorkshop)

    @Query("SELECT * FROM CarWorkshop WHERE id =:workId")
    suspend fun getCarWorkshop(workId: Long): CarWorkshopWithAll

    @Query("SELECT * FROM CarWorkshop")
    suspend fun getCarWorkshops(): List<CarWorkshop>
}