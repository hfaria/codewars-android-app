package com.hfaria.portfolio.codewars.persistence.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hfaria.portfolio.codewars.persistence.remote.api.Rank

class DbTypeConverters {

    @TypeConverter
    fun fromJsonString(jsonStr: String): List<Rank> {
        val gson = Gson()
        val itemType = object : TypeToken<List<Rank>>() {}.type
        val rankList = gson.fromJson<List<Rank>>(jsonStr, itemType)
        return rankList
    }

    @TypeConverter
    fun toJsonString(rankList: List<Rank>): String {
        return Gson().toJson(rankList);
    }
}