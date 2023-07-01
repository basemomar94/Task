package com.example.task.di

import android.content.Context
import com.example.task.models.Option
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides
    @Singleton
    fun providesRealmDatabase(
        @ApplicationContext context: Context
    ): Realm {
        Realm.init(context)
        val realmConfiguration = RealmConfiguration
            .Builder()
            .name("Task Project").deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
        return Realm.getDefaultInstance()
    }
}