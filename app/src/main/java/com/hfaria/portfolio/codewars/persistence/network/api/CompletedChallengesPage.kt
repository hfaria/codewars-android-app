package com.hfaria.portfolio.codewars.persistence.network.api

data class CompletedChallenge(
    var id: String,
    var name: String,
    var slug: String,
    var completedAt: String,
    var completedLanguages: ArrayList<String>
)

data class CompletedChallengesPage(
    var totalPages: Int,
    var totalItems: Int,
    var data: ArrayList<CompletedChallenge>
)