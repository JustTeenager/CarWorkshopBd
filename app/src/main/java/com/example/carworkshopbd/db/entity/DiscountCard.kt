package com.example.carworkshopbd.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.carworkshopbd.ShowableInList

@Entity
data class DiscountCard(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val number: String,
    val discount: Int,
) : ShowableInList {
    val subtype: Int
        get() = discount / 5

    override fun getUniqueId(): Long? = id

    override fun getTitle(): String = number

    override fun getDetails(): String {
        return "Скидка: $discount, тип: $subtype"
    }

    override fun getNum(): String = id.toString()
    override fun isFoundByQuery(query: String): Boolean {
        return number == query || discount.toString() == query
    }

    companion object {
        fun newInstance(
            firstString: String,
            secondString: String,
        ) = DiscountCard(
            number = firstString,
            discount = secondString.toInt()
        )
    }
}
