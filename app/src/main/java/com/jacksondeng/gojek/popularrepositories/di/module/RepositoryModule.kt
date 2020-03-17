package com.jacksondeng.gojek.popularrepositories.di.module

import android.content.SharedPreferences
import androidx.room.Room
import com.jacksondeng.gojek.popularrepositories.PopularRepositoriesApplication
import com.jacksondeng.gojek.githubrx.data.api.FetchRepositoriesApi
import cache.dao.RepoDao
import cache.db.DB_NAME
import cache.db.RepoDb
import com.jacksondeng.gojek.githubrx.data.repo.FetchRepositoriesRepo
import com.jacksondeng.gojek.githubrx.data.repo.FetchRepositoriesRepoImpl
import com.jacksondeng.gojek.popularrepositories.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @FragmentScope
    fun providesRoomDatabase(application: PopularRepositoriesApplication): RepoDb {
        return Room.databaseBuilder(application, RepoDb::class.java, DB_NAME).build()
    }

    @Provides
    @FragmentScope
    fun providesProductDao(db: RepoDb): RepoDao {
        return db.repoDao()
    }

    @Provides
    @FragmentScope
    fun providesRepositoriesRepo(
        api: FetchRepositoriesApi,
        dao: RepoDao,
        sharePref: SharedPreferences
    ): FetchRepositoriesRepo {
        return FetchRepositoriesRepoImpl(api, dao, sharePref)
    }
}