package com.jacksondeng.gojek.popularrepositories.di.builder

import com.jacksondeng.gojek.popularrepositories.views.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(
        modules = []
    )

    @Singleton
    abstract fun contributeMainActivity(): MainActivity
}