package com.example.carworkshopbd.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.carworkshopbd.ShowableInList
import java.math.BigDecimal
import kotlin.math.exp

@Entity(tableName = "Manager")
data class Manager(
    @PrimaryKey(autoGenerate = true)
    val managerId: Long? = null,
    val name: String,
    val experience: String,
    val salary: BigDecimal,
    val specialization: String,
    val number: String,
    var workId: Long? = null
) : ShowableInList {

    override fun getTitle(): String = name

    override fun getUniqueId(): Long? = managerId

    override fun getDetails(): String {
        return "Опыт: $experience, Специализация: $specialization"
    }

    override fun getNum(): String = managerId.toString()

    override fun isFoundByQuery(query: String): Boolean {
        return name == query || experience == query || salary.toString() == query ||
                specialization == query || number == query
    }

    companion object {
        fun newInstance(
            firstString: String,
            secondString: String,
            thirdString: String,
            fourthString: String,
            fifthString: String
        ) = Manager(
            name = firstString,
            experience = secondString,
            salary = BigDecimal(thirdString),
            specialization = fourthString,
            number = fifthString
        )
    }
}
