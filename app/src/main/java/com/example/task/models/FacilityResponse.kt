package com.example.task.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Entity(tableName = "exclusions")
data class FacilityResponse(
    @PrimaryKey
    var id: Int = 0,
    val exclusions: List<List<Exclusion>>,
    @Ignore
    val facilities: List<Facility>
) {
    constructor(id: Int, exclusions: List<List<Exclusion>>) : this(id, exclusions, emptyList())
}

class ExclusionConverter {
    @TypeConverter
    fun fromString(value: String?): List<List<Exclusion>> {
        val listType: Type = object : TypeToken<List<List<Exclusion>>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<List<Exclusion>>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}