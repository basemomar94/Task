package com.example.task.data.repository

import com.example.task.data.remote.ApiService
import com.example.task.models.FacilityResponse
import com.example.task.models.Option
import io.realm.Realm
import io.realm.RealmConfiguration

class MainRepositoryImplement(private val apiService: ApiService) :
    MainRepository {

    private val TAG = "demo_task"


    override suspend fun getRetailsData(): FacilityResponse = apiService.getFacilities()
    override suspend fun insertOptions(option: List<Option>) {

        val config: RealmConfiguration =
            RealmConfiguration.Builder()
                .name("Note.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1).build()
        val realm: Realm = Realm.getInstance(config)
        //val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        option.let { option ->
            realm.insertOrUpdate(option)
            realm.commitTransaction()
        }
    }
}