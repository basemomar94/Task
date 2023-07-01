package com.example.task.data.repository

import com.example.task.data.local.AppDao
import com.example.task.data.remote.ApiService
import com.example.task.models.Exclusion
import com.example.task.models.Facility
import com.example.task.models.FacilityResponse
import com.example.task.models.Option
import io.realm.Realm
import io.realm.RealmConfiguration

class MainRepositoryImplement(private val apiService: ApiService, private val dao: AppDao) :
    MainRepository {

    private val TAG = "demo_task"


    override suspend fun getRetailsData(): FacilityResponse = apiService.getFacilities()
    override suspend fun insertOptions(option: List<Option>) {
        dao.insertOptions(option)
    }

    override suspend fun getOptions(): List<Option> {
        return listOf()
    }

    override suspend fun insertExclusion(exclusionsList: List<Exclusion>) {
        dao.insertExclusion(exclusionsList)
    }

    override suspend fun getExclusion(): List<Exclusion> {
        return listOf()
    }

    override suspend fun insertFacilities(facilitiesList: List<Facility>) {
        dao.insertFacility(facilitiesList)
    }

    override suspend fun getFacilities(): List<Facility> {
        return listOf()
    }

}