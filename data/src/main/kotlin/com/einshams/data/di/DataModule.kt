package com.einshams.data.di

import com.einshams.data.remote.api.AuthApi
import com.einshams.data.repository.AuthRepositoryImpl
import com.einshams.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    companion object {
        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor().apply {
                level = if (com.einshams.data.BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }

            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                // reqres.in is often blocked by Cloudflare for mobile clients.
                .baseUrl("https://dummyjson.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideAuthApi(retrofit: Retrofit): AuthApi {
            return retrofit.create(AuthApi::class.java)
        }

        @Provides
        @Singleton
        fun provideAuthRepositoryImpl(authApi: AuthApi): AuthRepositoryImpl {
            return AuthRepositoryImpl(authApi)
        }
    }
}
