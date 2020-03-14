package com.jacksondeng.gojek.popularrepositories.data.api

import com.jacksondeng.gojek.popularrepositories.model.dto.RepoDTO
import io.reactivex.Flowable
import retrofit2.http.GET

const val BASE_URL = "https://github-trending-api.now.sh/"
const val TIMEOUT = 60L
const val LIST_REPOS = "repositories?"

interface FetchRepositoriesApi {
    @GET(LIST_REPOS)
    fun fetchRepositories(): Flowable<List<RepoDTO>>
}