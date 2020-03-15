package com.jacksondeng.gojek.popularrepositories

import android.content.Context
import androidx.multidex.MultiDex
import com.jacksondeng.gojek.popularrepositories.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class PopularRepositoriesApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}