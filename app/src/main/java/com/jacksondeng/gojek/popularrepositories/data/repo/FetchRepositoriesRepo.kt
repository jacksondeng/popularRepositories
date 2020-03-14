package com.jacksondeng.gojek.popularrepositories.data.repo

import com.jacksondeng.gojek.popularrepositories.data.api.FetchRepositoriesApi
import com.jacksondeng.gojek.popularrepositories.model.dto.RepoDTO
import com.jacksondeng.gojek.popularrepositories.model.entity.Repo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

interface FetchRepositoriesRepo {
    fun fetchRepositories(): Flowable<List<Repo>>
}

class FetchRepositoriesRepoImpl(private val api: FetchRepositoriesApi) : FetchRepositoriesRepo {
    override fun fetchRepositories(): Flowable<List<Repo>> {

        return Flowable.fromPublisher(
            api.fetchRepositories().retry(2)
                .doOnNext {
                    // TODO: Caching
                }
            /*.onErrorResumeNext(
                // TODO: Fall back to cached result
            )*/

        ).repeatWhen { flow: Flowable<Any> -> flow.delay(1, TimeUnit.SECONDS) }
            .onBackpressureLatest()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
            starts = dto.starts,
            forks = dto.forks
        )
    }
}