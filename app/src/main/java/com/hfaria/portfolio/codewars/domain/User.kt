package com.hfaria.portfolio.codewars.persistence.remote.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

class LanguageRanksDeserializer: JsonDeserializer<List<LanguageRank>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<LanguageRank> {
        val list: MutableList<LanguageRank> = mutableListOf()
        val jObj = json?.asJsonObject
        var languageRank: LanguageRank
        var langName: String
        var rank: Rank

        if (jObj != null) {
            val entrySet = jObj.entrySet()
            entrySet.iterator().forEach {
                if (it.value.isJsonObject) {
                    langName = it.key
                    rank = context!!.deserialize(it.value, Rank::class.java)
                    languageRank = LanguageRank(langName, rank)
                    list.add(languageRank)
                }
            }
        }

        return list
    }
}

data class LanguageRank(
    var language: String,
    var rank: Rank,
)

data class Rank(
    var rank: Int,
    var score: Int,
    @SerializedName("name")
    var title: String,
    var color: String,
) {
    fun getDescription(): String {
        return "$title ($score)"
    }
}

data class UserRanks(
    var overall: Rank,
    @JsonAdapter(LanguageRanksDeserializer::class)
    var languages: List<LanguageRank>,
) {

    fun getBestLanguageDescription(): String {
        val best: LanguageRank? = languages.maxByOrNull {
            it.rank.score
        }

        return if (best != null) "${best.language} (${best.rank.score})" else ""
    }
}

data class User(
    var username: String,
    var name: String?,
    var updatedAt: Int,
    var ranks: UserRanks?,
    var updatedAuthoredChallengesAt: Int,
)