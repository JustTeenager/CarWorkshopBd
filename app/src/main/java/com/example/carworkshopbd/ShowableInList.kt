package com.example.carworkshopbd

interface ShowableInList {
    fun getUniqueId(): Long?
    fun getTitle(): String
    fun getDetails(): String
    fun getNum(): String
    fun isFoundByQuery(query: String): Boolean
    fun equals(item: ShowableInList): Boolean {
        return getUniqueId() == item.getUniqueId() &&
                getTitle() == item.getTitle() &&
                getDetails() == item.getDetails() &&
                getNum() == item.getNum()
    }
}