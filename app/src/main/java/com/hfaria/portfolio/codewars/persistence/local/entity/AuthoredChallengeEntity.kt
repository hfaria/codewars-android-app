package com.hfaria.portfolio.codewars.persistence.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.hfaria.portfolio.codewars.persistence.remote.api.AuthoredChallenge
import com.hfaria.portfolio.codewars.persistence.remote.api.UserEntity

@Entity(tableName = "authored_challenge", foreignKeys = arrayOf(
    ForeignKey(entity = UserEntity::class,
        parentColumns = arrayOf("username"),
        childColumns = arrayOf("author"),
        onDelete = ForeignKey.CASCADE)
))
data class AuthoredChallengeEntity(
    @PrimaryKey
    var id: String,
    @ColumnInfo(index = true)
    var author: String,
    var name: String,
) {
    companion object {
        fun fromDomain(author:String, ch: AuthoredChallenge): AuthoredChallengeEntity {
            return AuthoredChallengeEntity(ch.id, author, ch.name)
        }

        fun toDomain(entity: AuthoredChallengeEntity): AuthoredChallenge {
            return AuthoredChallenge(
                entity.id, entity.name, "",
                null, null,
                arrayListOf(), arrayListOf()
            )
        }
    }
}