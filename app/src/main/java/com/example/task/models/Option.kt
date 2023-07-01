package com.example.task.models

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Option(
    var icon: String = "",
    @PrimaryKey
    var id: String = "",
    var name: String = "",
) : RealmObject(), Parcelable{
    override fun toString(): String {
        return "Option(icon='$icon', id='$id', name='$name')"
    }

}
