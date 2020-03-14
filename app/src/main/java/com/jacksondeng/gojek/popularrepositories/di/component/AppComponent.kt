package com.jacksondeng.gojek.popularrepositories.di.component

import com.jacksondeng.gojek.popularrepositories.PopularRepositoriesApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<PopularRepositoriesApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PopularRepositoriesApplication>()
}