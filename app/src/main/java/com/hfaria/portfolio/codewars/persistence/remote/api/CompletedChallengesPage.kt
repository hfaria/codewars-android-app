package com.hfaria.portfolio.codewars.persistence.remote.api

import com.hfaria.portfolio.codewars.persistence.local.entity.CompletedChallengeEntity

data class CompletedChallengesPage(
    var totalPages: Int,
    var totalItems: Int,
    var data: List<CompletedChallengeEntity>
)