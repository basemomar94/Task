package com.example.task.data.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.task.data.remote.ApiService
import com.example.task.models.Exclusion
import com.example.task.models.Facility
import com.example.task.models.FacilityResponse
import com.example.task.models.Option
import io.realm.Realm
import io.realm.RealmConfiguration

class MainRepositoryImplement(private val apiService: ApiService, private val realm: Realm) :
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

    override suspend fun getOptions(): List<Option> {
        TODO("Not yet implemented")
    }

    override suspend fun insertExclusion(exclusionsList: List<Exclusion>) {
        val config: RealmConfiguration =
            RealmConfiguration.Builder()
                .name("Note.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1).build()
        val realm: Realm = Realm.getInstance(config)
        //val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        exclusionsList.let { option ->
            realm.insertOrUpdate(option)
            realm.commitTransaction()
        }
    }

    override suspend fun getExclusion(): List<Exclusion> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFacilities(facilitiesList: List<Facility>) {
        val config: RealmConfiguration =
            RealmConfiguration.Builder()
                .name("Note.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1).build()
        val realm: Realm = Realm.getInstance(config)
        //val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        facilitiesList.let { option ->
            realm.insertOrUpdate(option)
            realm.commitTransaction()
        }
    }

    override suspend fun getFacilities(): List<Facility> {
        TODO("Not yet implemented")
    }
}