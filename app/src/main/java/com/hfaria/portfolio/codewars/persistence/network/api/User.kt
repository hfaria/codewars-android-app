package com.hfaria.portfolio.codewars.persistence.network.api

import com.google.gson.annotations.SerializedName

data class Rank(
    @SerializedName("rank")
    var rank: Int,
    @SerializedName("score")
    var score: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("color")
    var color: String,
)

data class UserRanks(
    @SerializedName("overall")
    var overall: Rank,
    @SerializedName("languages")
    var languages: ArrayList<Rank>,
)

data class CodeChallenges(
    @SerializedName("totalAuthored")
    var totalAuthored: Int,
    @SerializedName("totalCompleted")
    var totalComleted: Int
)

data class User(
    @SerializedName("username")
    var username: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("clan")
    var clan: String,
    @SerializedName("honor")
    var honor: Int,
    @SerializedName("leaderboardPosition")
    var leaderBoardPosition: String,
    @SerializedName("skills")
    var skills: ArrayList<String>,
    @SerializedName("codeChallenges")
    var codeChallengesCount: CodeChallenges,
    //@SerializedName("ranks")
    //var ranks: UserRanks
)