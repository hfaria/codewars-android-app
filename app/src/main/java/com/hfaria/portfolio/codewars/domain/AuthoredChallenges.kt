package com.hfaria.portfolio.codewars.persistence.remote.api

data class AuthoredChallenge(
    var id: String,
    var name: String,
    var description: String,
    var rank: Int?,
    var rankName: String?,
    var tags: List<String>,
    var languages: List<String>
)

data class AuthoredChallenges(
    var author: String,
    var data: List<AuthoredChallenge>
)