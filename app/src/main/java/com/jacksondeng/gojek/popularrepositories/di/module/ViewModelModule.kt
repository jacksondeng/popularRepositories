package com.jacksondeng.gojek.popularrepositories.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jacksondeng.gojek.common.util.ViewModelFactory
import com.jacksondeng.gojek.common.util.ViewModelKey
import com.jacksondeng.gojek.githubrx.viewmodel.FetchRepositoriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FetchRepositoriesViewModel::class)
    internal abstract fun fetchRepositoriesViewModel(viewModel: FetchRepositoriesViewModel): ViewModel
}