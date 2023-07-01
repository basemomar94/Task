package com.example.task.models

import android.os.Parcelable
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Exclusion(
    @PrimaryKey
    var id: String = "",
    var facility_id: String = "",
    var options_id: String = ""
) : RealmObject(), Parcelable {
    override fun toString(): String {
        return "Exclusion(id='$id', facility_id='$facility_id',options_id='$options_id')"
    }
}