package com.example.task.models

import android.os.Parcelable
import androidx.room.Entity
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

@Entity
data class Facility(
    @androidx.room.PrimaryKey
    var id: String = "",
    var facility_id: String = "",
    var name: String = "",
    @androidx.room.Ignore
    var options: List<Option> = listOf()
)