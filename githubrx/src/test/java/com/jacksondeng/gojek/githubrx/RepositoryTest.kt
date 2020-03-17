package com.jacksondeng.gojek.githubrx

import android.accounts.NetworkErrorException
import android.content.SharedPreferences
import com.jacksondeng.gojek.githubrx.data.api.FetchRepositoriesApi
import cache.dao.RepoDao
import com.jacksondeng.gojek.common.model.dto.BuilderDTO
import com.jacksondeng.gojek.common.model.dto.RepoDTO
import com.jacksondeng.gojek.common.model.entity.Repo
import com.jacksondeng.gojek.githubrx.data.repo.FetchRepositoriesRepoImpl
import com.jacksondeng.gojek.githubrx.data.repo.TAG_LAST_CACHED_TIME
import com.jacksondeng.gojek.common.util.TrampolineSchedulerProvider
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

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
            stars = 5641,
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

    private val repoDao = mockk<RepoDao>(relaxed = true)

    private val sharePref = mockk<SharedPreferences>(relaxed = true)

    lateinit var repo: FetchRepositoriesRepoImpl

    @Before
    internal fun setUp() {
        repo = FetchRepositoriesRepoImpl(api, repoDao, sharePref)
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

        val observer = repo.fetchRepositories(TrampolineSchedulerProvider())
            .test()
            .assertValue(
                listOf(
                    Repo(
                        author = "google",
                        name = "mediapipe",
                        avatarUrl = "https://github.com/google.png",
                        repoUrl = "https://github.com/google/mediapipe",
                        description = "MediaPipe is a cross-platform framework for building multimodal applied machine learning pipelines",
                        language = "C++",
                        languageColor = "#f34b7d",
                        stars = 5641,
                        forks = 977
                    )
                )
            )

        observer.dispose()
    }

    @Test
    internal fun `mapToModel test`() {
        val expectedRepo = listOf(
            Repo(
                author = "google",
                name = "mediapipe",
                avatarUrl = "https://github.com/google.png",
                repoUrl = "https://github.com/google/mediapipe",
                description = "MediaPipe is a cross-platform framework for building multimodal applied machine learning pipelines",
                language = "C++",
                languageColor = "#f34b7d",
                stars = 5641,
                forks = 977
            )
        )

        val mappedRepo = dummyResponse.map {
            repo.mapToModel(it)
        }

        Assert.assertTrue(expectedRepo == mappedRepo)
    }

    @Test
    internal fun `api failed, fallback to cache value test`() {
        every { api.fetchRepositories() } returns Flowable.error(NetworkErrorException())

        every { sharePref.getLong(TAG_LAST_CACHED_TIME, -1L) } returns 1234L

        val mockResponse = dummyResponse.map {
            repo.mapToModel(it)
        }

        every { repoDao.getCachedRepos() } returns Single.just(
            mockResponse
        )

        val observer = repo.fetchRepositories(TrampolineSchedulerProvider())
            .test()
            .assertValue {
                it.isNotEmpty()
            }
            .assertTerminated()

        observer.dispose()
    }

    @Test
    internal fun `api failed, cache is empty test`() {
        every { api.fetchRepositories() } returns Flowable.error(NetworkErrorException())

        every { sharePref.getLong(TAG_LAST_CACHED_TIME, -1L) } returns -1L

        every { repoDao.getCachedRepos() } returns Single.just(emptyList())

        val observer = repo.fetchRepositories(TrampolineSchedulerProvider())
            .test()
            .assertValue {
                it.isEmpty()
            }
            .assertTerminated()

        observer.dispose()
    }
}