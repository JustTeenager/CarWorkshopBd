package com.example.carworkshopbd.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.carworkshopbd.ShowableInList
import java.math.BigDecimal

@Entity
data class Mechanic(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val experience: String,
    val rating: Float,
    val salary: BigDecimal,
    var workId: Long? = null
) : ShowableInList {
    override fun getTitle(): String = name

    override fun getUniqueId(): Long? = id

    override fun getDetails(): String {
        return "Опыт: ${experience}, Рейтинг: $rating"
    }

    override fun getNum(): String = id.toString()
    override fun isFoundByQuery(query: String): Boolean {
        return name == query || experience == query || rating.toString() == query ||
                salary.toString() == query
    }

    companion object {
        fun newInstance(
            firstString: String,
            secondString: String,
            thirdString: String,
            fourthString: String
        ) = Mechanic(
            name = firstString,
            experience = secondString,
            rating = thirdString.toFloat(),
            salary = BigDecimal(fourthString)
        )
    }
}
