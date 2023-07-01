package com.example.task.models

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey


open class Facility(
    @PrimaryKey
    var id: String = "",
    var facility_id: String = "",
    var name: String = "",
    @Ignore
    var options: List<Option> = listOf()
) : RealmObject() {

}