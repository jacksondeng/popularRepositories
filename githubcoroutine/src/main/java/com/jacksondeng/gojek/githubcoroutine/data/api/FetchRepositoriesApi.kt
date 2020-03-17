package com.jacksondeng.gojek.githubcoroutine.data.api

import com.jacksondeng.gojek.common.model.dto.RepoDTO
import retrofit2.http.GET

const val BASE_URL = "https://github-trending-api.now.sh/"
const val TIMEOUT = 60L
const val LIST_REPOS = "repositories?"
const val RELOAD_TIME = 30L

interface FetchRepositoriesApi {
    @GET(LIST_REPOS)
    suspend fun fetchRepositories(): List<RepoDTO>
}