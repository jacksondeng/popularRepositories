package com.jacksondeng.gojek.githubcoroutine.data.repo

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import cache.dao.RepoDao
import com.jacksondeng.gojek.common.model.dto.RepoDTO
import com.jacksondeng.gojek.common.model.entity.Repo
import com.jacksondeng.gojek.githubcoroutine.data.api.FetchRepositoriesApi
import org.joda.time.Interval
import javax.inject.Inject

const val TAG_LAST_CACHED_TIME = "lastCachedTime"

interface FetchRepositoriesRepo {
    suspend fun fetchRepositories(): List<Repo>
}

class FetchRepositoriesRepoImpl @Inject constructor(
    private val api: FetchRepositoriesApi,
    private val reposDao: RepoDao,
    private val sharePref: SharedPreferences
) : FetchRepositoriesRepo {

    override suspend fun fetchRepositories(): List<Repo> {
        return try {
            api.fetchRepositories().map {
                mapToModel(it)
            }.run {
                cacheRepos(this)
                this
            }
        } catch (e: Exception) {
            if (sharePref.getLong(TAG_LAST_CACHED_TIME, -1L) != -1L)
                reposDao.getCachedReposSuspend()
            else
                emptyList()
        }
    }

    @VisibleForTesting
    fun mapToModel(dto: RepoDTO): Repo {
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