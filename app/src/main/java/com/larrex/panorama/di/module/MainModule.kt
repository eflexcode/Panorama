package com.larrex.panorama.di.module

import com.google.firebase.auth.FirebaseAuth
import com.larrex.panorama.di.repository.RepositoryImpl
import com.larrex.panorama.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
abstract class MainModule {

    @Singleton
    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository



}