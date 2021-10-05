package com.hfaria.portfolio.codewars.persistence.remote.api

data class AuthoredChallenge(
    var id: String,
    var name: String,
    var description: String,
    var rank: Int?,
    var rankName: String?,
    var tags: ArrayList<String>,
    var languages: ArrayList<String>
)

data class AuthoredChallenges(
    var data: ArrayList<AuthoredChallenge>
)