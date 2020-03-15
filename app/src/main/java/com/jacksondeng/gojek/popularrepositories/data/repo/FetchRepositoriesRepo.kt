package com.jacksondeng.gojek.popularrepositories.data.repo

import android.content.SharedPreferences
import com.jacksondeng.gojek.popularrepositories.data.api.FetchRepositoriesApi
import com.jacksondeng.gojek.popularrepositories.data.cache.dao.RepoDao
import com.jacksondeng.gojek.popularrepositories.di.module.TAG_LAST_CACHED_TIME
import com.jacksondeng.gojek.popularrepositories.model.dto.RepoDTO
import com.jacksondeng.gojek.popularrepositories.model.entity.Repo
import com.jacksondeng.gojek.popularrepositories.util.BaseSchedulerProvider
import io.reactivex.Flowable
import org.joda.time.Interval
import javax.inject.Inject

interface FetchRepositoriesRepo {
    fun fetchRepositories(scheduler: BaseSchedulerProvider): Flowable<List<Repo>>
}

class FetchRepositoriesRepoImpl @Inject constructor(
    private val api: FetchRepositoriesApi,
    private val reposDao: RepoDao,
    private val sharePref: SharedPreferences
) :

    FetchRepositoriesRepo {
    override fun fetchRepositories(scheduler: BaseSchedulerProvider): Flowable<List<Repo>> {

        return Flowable.fromPublisher(
            api.fetchRepositories().retry(2)
                .doOnNext {
                    cacheRepos(it.map { dto ->
                        mapToModel(dto)
                    })
                }
            )
            //.repeatWhen { flow: Flowable<Any> -> flow.delay(RELOAD_TIME, TimeUnit.SECONDS) }
            .onBackpressureLatest()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .flatMap {
                Flowable.just(it.map { dto ->
                    mapToModel(dto)
                })
            }
    }

    private fun mapToModel(dto: RepoDTO): Repo {
        return Repo(
            author = dto.author,
            name = dto.name,
            avatarUrl = dto.avatarUrl ?: "",
            repoUrl = dto.repoUrl ?: "",
            description = dto.description,
            language = dto.language ?: "",
            languageColor = dto.languageColor ?: "",
            stars = dto.stars,
            forks = dto.forks
        )
    }

    private fun cacheRepos(repos: List<Repo>) {
        val lastCachedTime = sharePref.getLong(TAG_LAST_CACHED_TIME, -1L)
        if (lastCachedTime == -1L) {
            // If lastCachedTime == -1 -> there were no cached data -> updateCache
            updateCache(repos)
        } else {
            val interval = Interval(lastCachedTime, System.currentTimeMillis())
            // If lastCachedTime == 2hours -> clear and update Cache
            if (interval.toDuration().standardHours >= 2) {
                clearCache()
                updateCache(repos)
            }
        }
    }

    private fun clearCache() = reposDao.clearCache()

    private fun updateCache(repos: List<Repo>) {
        reposDao.updateCache(repos)
        sharePref.edit()
            .putLong(TAG_LAST_CACHED_TIME, System.currentTimeMillis())
            .apply()
    }
}