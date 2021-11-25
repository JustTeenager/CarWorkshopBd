package com.example.carworkshopbd.db.dao

import androidx.room.*
import com.example.carworkshopbd.db.entity.Client
import com.example.carworkshopbd.db.entity.releation.ClientAll

@Dao
interface ClientDao {

    @Query("SELECT * FROM Client WHERE id =:clientId")
    suspend fun getClient(clientId: Long): ClientAll

    @Query("SELECT *  FROM Client")
    suspend fun getClients(): List<Client>

    @Insert
    suspend fun insertClient(client: Client)

    @Delete
    suspend fun deleteClient(client: Client)

    @Query("DELETE FROM Automobile WHERE autoId =:autoId")
    suspend fun deleteAuto(autoId: Long)

    @Query("DELETE FROM DiscountCard WHERE id =:discountId")
    suspend fun deleteDiscountCard(discountId: Long)

    @Transaction
    suspend fun deleteClientAll(client: Client) {
        deleteClient(client)
        deleteAuto(client.autoId ?: 0)
        client.discountId?.let {
            deleteDiscountCard(it)
        }
    }
}