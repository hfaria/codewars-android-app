package com.hfaria.portfolio.codewars.persistence.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.hfaria.portfolio.codewars.persistence.remote.api.UserEntity

@Entity(tableName = "completed_challenge", foreignKeys = arrayOf(
    ForeignKey(entity = UserEntity::class,
        parentColumns = arrayOf("username"),
        childColumns = arrayOf("author"),
        onDelete = ForeignKey.CASCADE)
))
data class CompletedChallengeEntity(
    @PrimaryKey
    var id: String,
    var author: String,
    var name: String,
    var slug: String,
    var completedAt: String,
    //var completedLanguages: List<String>
)