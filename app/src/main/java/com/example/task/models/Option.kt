package com.example.task.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import kotlinx.android.parcel.Parcelize

@Entity
data class Option(
    var icon: String = "",
    @androidx.room.PrimaryKey
    var id: String = "",
    var name: String = "",
    @Ignore
    var isSelected: Boolean = false
)
