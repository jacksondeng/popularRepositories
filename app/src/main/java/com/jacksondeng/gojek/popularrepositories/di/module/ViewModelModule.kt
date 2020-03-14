package com.jacksondeng.gojek.popularrepositories.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jacksondeng.gojek.popularrepositories.util.ViewModelFactory
import com.jacksondeng.gojek.popularrepositories.util.ViewModelKey
import com.jacksondeng.gojek.popularrepositories.viewmodel.FetchRepositoriesViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FetchRepositoriesViewModel::class)
    internal abstract fun fetchRepositoriesViewModel(viewModel: FetchRepositoriesViewModel): ViewModel
}