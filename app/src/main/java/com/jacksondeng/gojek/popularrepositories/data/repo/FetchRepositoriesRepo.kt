package com.jacksondeng.gojek.popularrepositories.data.repo

import com.jacksondeng.gojek.popularrepositories.data.api.FetchRepositoriesApi
import com.jacksondeng.gojek.popularrepositories.model.dto.RepoDTO
import com.jacksondeng.gojek.popularrepositories.model.entity.Repo
import com.jacksondeng.gojek.popularrepositories.util.BaseSchedulerProvider
import io.reactivex.Flowable

interface FetchRepositoriesRepo {
    fun fetchRepositories(scheduler: BaseSchedulerProvider): Flowable<List<Repo>>
}

class FetchRepositoriesRepoImpl(private val api: FetchRepositoriesApi) : FetchRepositoriesRepo {
    override fun fetchRepositories(scheduler: BaseSchedulerProvider): Flowable<List<Repo>> {

        return Flowable.fromPublisher(
            api.fetchRepositories().retry(2)
                .doOnNext {
                    // TODO: Caching
                }
            /*.onErrorResumeNext(
                // TODO: Fall back to cached result
            )*/

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
}