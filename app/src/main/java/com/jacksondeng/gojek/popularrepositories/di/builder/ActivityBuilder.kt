package com.jacksondeng.gojek.popularrepositories.di.builder

import com.jacksondeng.gojek.popularrepositories.di.scope.ActivityScope
import com.jacksondeng.gojek.popularrepositories.views.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(
        modules = []
    )

    @ActivityScope
    internal abstract fun contributeMainActivity(): MainActivity
}