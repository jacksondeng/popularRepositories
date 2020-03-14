package com.jacksondeng.gojek.popularrepositories.di.module

import com.jacksondeng.gojek.popularrepositories.data.api.FetchRepositoriesApi
import com.jacksondeng.gojek.popularrepositories.data.repo.FetchRepositoriesRepo
import com.jacksondeng.gojek.popularrepositories.data.repo.FetchRepositoriesRepoImpl
import com.jacksondeng.gojek.popularrepositories.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    @FragmentScope
    fun providesRepositoriesRepo(
        api: FetchRepositoriesApi
    ): FetchRepositoriesRepo {
        return FetchRepositoriesRepoImpl(api)
    }
}