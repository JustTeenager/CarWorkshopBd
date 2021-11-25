package com.example.carworkshopbd.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.carworkshopbd.ShowableInList
import java.math.BigDecimal

@Entity(tableName = "CarWorkshop")
data class CarWorkshop(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val address: String,
    val income: BigDecimal,
) : ShowableInList {
    override fun getUniqueId(): Long? = id

    override fun getTitle(): String = address

    override fun getDetails(): String {
        return "Доход: $income"
    }

    override fun getNum(): String = id.toString()

    override fun isFoundByQuery(query: String): Boolean {
        return address == query || income.toString() == query
    }

    companion object {
        fun newInstance(
            firstString: String,
            secondString: String,
        ) = CarWorkshop(
            address = firstString,
            income = BigDecimal(secondString)
        )
    }
}
