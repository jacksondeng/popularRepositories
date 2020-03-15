package com.jacksondeng.gojek.popularrepositories.di.module

import com.jacksondeng.gojek.popularrepositories.data.api.BASE_URL
import com.jacksondeng.gojek.popularrepositories.data.api.FetchRepositoriesApi
import com.jacksondeng.gojek.popularrepositories.data.api.TIMEOUT
import com.jacksondeng.gojek.popularrepositories.di.scope.FragmentScope
import com.jacksondeng.gojek.popularrepositories.util.Tls12SocketFactory.Companion.enableTls12
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {
    @Provides
    @FragmentScope
    fun providesHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .enableTls12()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

    @Provides
    @FragmentScope
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @FragmentScope
    fun provideRepoApi(retrofit: Retrofit): FetchRepositoriesApi {
        return retrofit.create(FetchRepositoriesApi::class.java)
    }
}