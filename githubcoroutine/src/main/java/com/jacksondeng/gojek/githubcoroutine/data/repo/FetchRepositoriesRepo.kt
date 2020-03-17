package com.jacksondeng.gojek.githubcoroutine.data.repo

import android.content.SharedPreferences
import cache.dao.RepoDao
import com.jacksondeng.gojek.common.model.entity.Repo
import com.jacksondeng.gojek.githubcoroutine.data.api.FetchRepositoriesApi
import io.reactivex.Flowable
import javax.inject.Inject

const val TAG_LAST_CACHED_TIME = "lastCachedTime"
/*

interface FetchRepositoriesRepo {
    fun fetchRepositories(scheduler: BaseSchedulerProvider): Flowable<List<Repo>>
}

class FetchRepositoriesRepoImpl @Inject constructor(
    private val api: FetchRepositoriesApi,
    private val reposDao: RepoDao,
    private val sharePref: SharedPreferences
) :*/
