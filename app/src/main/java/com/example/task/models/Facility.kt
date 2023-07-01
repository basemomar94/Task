package com.example.task.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

@Entity
data class Facility(
    @androidx.room.PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var facility_id: String = "",
    var name: String = "",
    var options: List<Option> = listOf()
)

class OptionsConverter {
    @TypeConverter
    fun fromString(value: String): List<Option> {
        val listType = object : TypeToken<List<Option>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(options: List<Option>): String {
        return Gson().toJson(options)
    }
}