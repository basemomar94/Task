package com.example.task.data.repository

import com.example.task.data.local.AppDao
import com.example.task.data.remote.ApiService
import com.example.task.models.Exclusion
import com.example.task.models.Facility
import com.example.task.models.FacilityResponse
import com.example.task.models.Option

class MainRepositoryImplement(private val apiService: ApiService, private val dao: AppDao) :
    MainRepository {


    override suspend fun getRetailsData(): FacilityResponse = apiService.getFacilities()
    override suspend fun insertOptions(option: List<Option>) {
        dao.insertOptions(option)
    }

    override suspend fun getOptions(): List<Option> {
        return listOf()
    }

    override suspend fun insertExclusion(facilityResponse: FacilityResponse) {
        dao.insertExclusion(facilityResponse)
    }

    override suspend fun getExclusion(): FacilityResponse {
        return dao.getExclusions()
    }

    override suspend fun insertFacilities(facilitiesList: List<Facility>) {
        dao.insertFacility(facilitiesList)
    }

    override suspend fun getFacilities(): List<Facility> {
        return dao.getFacilities()
    }

}