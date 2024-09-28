package io.github.rysanekrivera.domain.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.rysanekrivera.data.apis.HiringApi
import io.github.rysanekrivera.domain.repositories.HiringRepository
import io.github.rysanekrivera.domain.repositories.HiringRepositoryImpl
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    @Singleton
    @Provides
    fun provideRetrofitInstance(client: OkHttpClient): Retrofit {
        val json = Json { ignoreUnknownKeys = true  }
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Singleton
    @Provides
    fun provideHiringApi(retrofit: Retrofit): HiringApi = retrofit.create(HiringApi::class.java)

    @Singleton
    @Provides
    fun provideHiringRepository(repository: HiringRepositoryImpl): HiringRepository = repository

}