package com.jacksondeng.gojek.popularrepositories.di.builder

import com.jacksondeng.gojek.popularrepositories.di.module.NetworkModule
import com.jacksondeng.gojek.popularrepositories.di.module.RepositoryModule
import com.jacksondeng.gojek.popularrepositories.di.module.ViewModelModule
import com.jacksondeng.gojek.popularrepositories.di.scope.FragmentScope
import com.jacksondeng.gojek.popularrepositories.views.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector(
        modules = [NetworkModule::class, RepositoryModule::class, ViewModelModule::class]
    )

    @FragmentScope
    internal abstract fun contributeMainFragment(): MainFragment
}