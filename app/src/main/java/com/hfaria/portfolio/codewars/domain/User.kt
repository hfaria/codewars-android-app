package com.hfaria.portfolio.codewars.persistence.remote.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

class LanguageRanksDeserializer: JsonDeserializer<List<Rank>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<Rank> {
        val list: MutableList<Rank> = mutableListOf()
        val jObj = json?.asJsonObject
        var rank: Rank

        if (jObj != null) {
            val entrySet = jObj.entrySet()
            entrySet.iterator().forEach {
                if (it.value.isJsonObject) {
                    rank = context!!.deserialize(it.value, Rank::class.java)
                    list.add(rank)
                }
            }
        }

        return list
    }
}

data class Rank(
    var rank: Int,
    var score: Int,
    @SerializedName("name")
    var title: String,
    var color: String,
)

data class UserRanks(
    var overall: Rank,
    @JsonAdapter(LanguageRanksDeserializer::class)
    var languages: List<Rank>,
)

data class User(
    var username: String,
    var name: String?,
    var updatedAt: Int,
    var ranks: UserRanks
)