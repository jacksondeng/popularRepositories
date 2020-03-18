package com.jacksondeng.gojek.githubcoroutine.data.api

import com.jacksondeng.gojek.common.model.dto.RepoDTO
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Inject

class FetchRepositoriesApiImpl @Inject constructor(private val retrofit: Retrofit) :
    FetchRepositoriesApi {
    override suspend fun fetchRepositories(): List<RepoDTO> {
        return retrofit
            .create(FetchRepositoriesApi::class.java)
            .fetchRepositories()
    }
}