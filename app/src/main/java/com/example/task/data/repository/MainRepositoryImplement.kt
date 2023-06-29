package com.example.task.data.repository

import com.example.task.data.remote.ApiService
import com.example.task.models.FacilityResponse
import com.example.task.models.Option
import io.realm.Realm

class MainRepositoryImplement(private val apiService: ApiService, private val realm: Realm) :
    MainRepository {

    override suspend fun getRetailsData(): FacilityResponse = apiService.getFacilities()
    override suspend fun insertOptions(option: Option) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realmInstance ->
            realmInstance.copyToRealmOrUpdate(option)
        }
        realm.close()
    }
}