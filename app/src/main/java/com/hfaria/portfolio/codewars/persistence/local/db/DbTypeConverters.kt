package com.hfaria.portfolio.codewars.persistence.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hfaria.portfolio.codewars.persistence.remote.api.LanguageRank

class DbTypeConverters {

    @TypeConverter
    fun fromJsonString(jsonStr: String): List<LanguageRank> {
        val gson = Gson()
        val itemType = object : TypeToken<List<LanguageRank>>() {}.type
        return gson.fromJson(jsonStr, itemType)
    }

    @TypeConverter
    fun toJsonString(rankList: List<LanguageRank>): String {
        return Gson().toJson(rankList);
    }
}