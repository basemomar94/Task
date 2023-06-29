package com.example.task.data.remote

import com.example.task.models.FacilityResponse
import retrofit2.http.GET

interface ApiService {


     @GET("ad-assignment/db")
     suspend fun getFacilities():FacilityResponse

}