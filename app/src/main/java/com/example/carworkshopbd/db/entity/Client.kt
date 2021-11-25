package com.example.carworkshopbd.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.carworkshopbd.ShowableInList

@Entity
data class Client(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val number: String,
    var autoId: Long? = null,
    var discountId: Long? = null,
    var workId: Long? = null
) : ShowableInList {
    override fun getUniqueId(): Long? = id

    override fun getTitle(): String = name

    override fun getDetails(): String {
        val discountCardInfo =
            if (discountId != null) "есть скидочная карта" else "скидочной карты нет"
        return "Телефон: $number, $discountCardInfo"
    }

    override fun getNum(): String = id.toString()
    override fun isFoundByQuery(query: String): Boolean {
        return name == query || number == query
    }

    companion object {
        fun newInstance(
            firstString: String,
            secondString: String,
        ) = Client(
            name = firstString,
            number = secondString
        )
    }

}
