package com.jacksondeng.gojek.popularrepositories.di.module

import android.content.Context
import android.content.SharedPreferences
import com.jacksondeng.gojek.popularrepositories.PopularRepositoriesApplication
import com.jacksondeng.gojek.popularrepositories.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

const val PREF_FILE_NAME = "repos.pref"

@Module
class SharePrefModule {
    @Provides
    @FragmentScope
    fun provideSharedPreference(application: PopularRepositoriesApplication): SharedPreferences {
        return application.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }
}
