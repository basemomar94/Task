package com.example.task.data.repository

import com.example.task.models.FacilityResponse
import com.example.task.models.Option

interface MainRepository {

    suspend fun getRetailsData(): FacilityResponse

    suspend fun insertOptions(optionsList: List<Option>)

}