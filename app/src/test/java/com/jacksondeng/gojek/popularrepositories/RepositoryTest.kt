package com.jacksondeng.gojek.popularrepositories

import com.jacksondeng.gojek.popularrepositories.data.api.FetchRepositoriesApi
import com.jacksondeng.gojek.popularrepositories.data.repo.FetchRepositoriesRepoImpl
import com.jacksondeng.gojek.popularrepositories.model.dto.BuilderDTO
import com.jacksondeng.gojek.popularrepositories.model.dto.RepoDTO
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

class RepositoryTest {

    private val dummyResponse = listOf(
        RepoDTO(
            author = "google",
            name = "mediapipe",
            avatarUrl = "https://github.com/google.png",
            repoUrl = "https://github.com/google/mediapipe",
            description = "MediaPipe is a cross-platform framework for building multimodal applied machine learning pipelines",
            language = "C++",
            languageColor = "#f34b7d",
            starts = 5641,
            forks = 977,
            currentPeriodStars = 213,
            builer = listOf(
                BuilderDTO(
                    username = "chuoling",
                    githubProfileUrl = "https://github.com/chuoling",
                    avatarUrl = "https://avatars0.githubusercontent.com/u/4025056"
                )

            )
        )
    )

    private val api = mockk<FetchRepositoriesApi>()

    lateinit var repo: FetchRepositoriesRepoImpl

    @Before
    internal fun setUp() {
        repo = FetchRepositoriesRepoImpl(api)
        RxJavaPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }
    }

    @After
    fun teardown() {
        RxJavaPlugins.reset()
    }

    @Test
    internal fun `fetch repo success test`() {
        every { api.fetchRepositories() } returns Flowable.just(
            dummyResponse
        )

        /*val subscriber = repo.fetchRepositories()
            .test()
            .assertValue(

            )
        subscriber.dispose()
         */
    }
}