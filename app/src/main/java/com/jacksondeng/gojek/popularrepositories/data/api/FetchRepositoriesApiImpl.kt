package com.jacksondeng.gojek.popularrepositories.data.api

import com.jacksondeng.gojek.popularrepositories.model.dto.RepoDTO
import io.reactivex.Flowable
import retrofit2.Retrofit
import javax.inject.Inject

class FetchRepositoriesApiImpl @Inject constructor(private val retrofit: Retrofit) :
    FetchRepositoriesApi {
    override fun fetchRepositories(): Flowable<List<RepoDTO>> {
        return retrofit
            .create(FetchRepositoriesApi::class.java)
            .fetchRepositories()
    }
}