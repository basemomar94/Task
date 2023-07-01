package com.example.task.data.repository

import com.example.task.models.Exclusion
import com.example.task.models.Facility
import com.example.task.models.FacilityResponse
import com.example.task.models.Option

interface MainRepository {

    suspend fun getRetailsData(): FacilityResponse

    suspend fun insertOptions(option: List<Option>)

    suspend fun getOptions(): List<Option>

    suspend fun insertExclusion(exclusionsList: List<Exclusion>)

    suspend fun getExclusion(): List<Exclusion>

    suspend fun insertFacilities(facilitiesList: List<Facility>)

    suspend fun getFacilities(): List<Facility>


}