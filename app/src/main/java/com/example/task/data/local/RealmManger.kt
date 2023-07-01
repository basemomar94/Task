package com.example.task.data.local

import com.example.task.models.Option
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults

// Note Manager Class Responsible for creating Realm Database to save data
class RealmManager {
    private val config: RealmConfiguration =
        RealmConfiguration.Builder()
            .name("Facilities.realm")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(1).build()
    private val realm: Realm = Realm.getInstance(config)

    //use it to save or update the data
    fun addOption(option: Option) {
        realm.beginTransaction()
        option.let { option ->
            realm.insertOrUpdate(option)
            realm.commitTransaction()
        }
    }

/*    //read all data
    val getAllNotes: RealmResults<Note>
        get() = realm.where(Note::class.java).findAll()


    //delete one item from database
    fun deleteFromRealm(id: String) {
        val itemRealmObject = realm.where(Note::class.java).equalTo("id", id).findFirst()
        if (itemRealmObject != null) {
            realm.beginTransaction()
            itemRealmObject.deleteFromRealm()
            realm.commitTransaction()
        }
    }*/

    //delete all items in the database
    fun deleteAllFromRealm(){
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
    }
}