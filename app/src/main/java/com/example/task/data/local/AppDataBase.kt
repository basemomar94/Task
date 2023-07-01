package com.example.task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task.models.Exclusion
import com.example.task.models.Facility
import com.example.task.models.Option

@Database(
    entities = [Exclusion::class, Facility::class, Option::class], version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract val dao: AppDao
}