package com.example.task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.task.models.*

@Database(
    entities = [Facility::class, Option::class, FacilityResponse::class],
    version = 1
)
@TypeConverters(OptionsConverter::class, ExclusionConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract val dao: AppDao
}