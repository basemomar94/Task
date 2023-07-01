package com.example.task.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import com.example.task.models.Exclusion
import com.example.task.models.Facility
import com.example.task.models.Option

@Dao
interface AppDao {


    @Insert(onConflict = REPLACE)
    suspend fun insertOptions(options: List<Option>)

    @Insert(onConflict = REPLACE)
    suspend fun insertFacility(facilities: List<Facility>)

    @Insert(onConflict = REPLACE)
    suspend fun insertExclusion(exclusions: List<Exclusion>)
}