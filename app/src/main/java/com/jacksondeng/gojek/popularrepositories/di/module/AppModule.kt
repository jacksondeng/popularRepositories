package com.jacksondeng.gojek.popularrepositories.di.module

import android.content.Context
import com.jacksondeng.gojek.popularrepositories.PopularRepositoriesApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: PopularRepositoriesApplication): Context {
        return application
    }
}