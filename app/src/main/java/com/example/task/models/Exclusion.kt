package com.example.task.models

import android.os.Parcelable
import androidx.room.Entity
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize
@Entity
data class Exclusion(
    @androidx.room.PrimaryKey
    var id: String = "",
    var facility_id: String = "",
    var options_id: String = ""
)
