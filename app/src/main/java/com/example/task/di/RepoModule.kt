package com.example.task.di

import com.example.task.data.remote.ApiService
import com.example.task.data.repository.MainRepository
import com.example.task.data.repository.MainRepositoryImplement
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.Realm


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

     @Provides
    fun provideRepo(apiService: ApiService,realm: Realm): MainRepository = MainRepositoryImplement(apiService)
}