package com.jacksondeng.gojek.popularrepositories.di.component

import com.jacksondeng.gojek.popularrepositories.PopularRepositoriesApplication
import com.jacksondeng.gojek.popularrepositories.di.builder.ActivityBuilder
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuilder::class])
interface AppComponent : AndroidInjector<PopularRepositoriesApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PopularRepositoriesApplication>()
}