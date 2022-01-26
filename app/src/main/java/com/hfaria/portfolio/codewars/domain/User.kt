package com.hfaria.portfolio.codewars.domain

import com.google.gson.annotations.SerializedName

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

class Ranks: HashMap<String, LanguageRank>()

data class User(
    var username: String,
    var name: String?,
    var updatedAt: Int,
    // TODO fix this
    //var ranks: Ranks,
)

// TODO Implement this
//fun getBestLanguageDescription(): String {
//val best: LanguageRank? = languages.maxByOrNull {
//    it.rank.score
//}

//return if (best != null) "${best.language} (${best.rank.score})" else ""
//return ""
//}
