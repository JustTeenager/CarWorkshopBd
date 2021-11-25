package com.example.carworkshopbd.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.carworkshopbd.db.entity.DiscountCard

@Dao
interface DiscountCardDao {

    @Query("SELECT * FROM DiscountCard")
    suspend fun getDiscountCards(): List<DiscountCard>

    @Query("SELECT * FROM DiscountCard WHERE id =:discountId")
    suspend fun getDiscountCard(discountId: Long): DiscountCard

    @Insert
    suspend fun insertDiscountCard(discountCard: DiscountCard)

    @Delete
    suspend fun deleteDiscountCard(discountCard: DiscountCard)
}