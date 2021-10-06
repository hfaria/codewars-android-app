package com.hfaria.portfolio.codewars.domain

data class ChallengeProfile(
    var id: String,
    var name: String,
    var slug: String,
    var url: String,
    var category: String,
    var description: String,
    var tags: List<String>,
    var languages: List<String>,
    var totalAttempts: Int,
    var totalCompleted: Int,
    var totalStars: Int,
    var voteScore: Int,
    var publishedAt: String,
    var approvedAt : String
)