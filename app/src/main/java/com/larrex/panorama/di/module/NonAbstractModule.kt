package com.larrex.panorama.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.larrex.panorama.Util
import com.larrex.panorama.domain.retrofit.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
class NonAbstractModule {

    @Singleton
    @Provides
    fun provideAuth(): FirebaseAuth {

        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFireStore(): FirebaseFirestore {

        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(Util.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

    }

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

}