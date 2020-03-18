package com.jacksondeng.gojek.githubcoroutine

import android.accounts.NetworkErrorException
import android.content.SharedPreferences
import cache.dao.RepoDao
import com.jacksondeng.gojek.common.model.dto.BuilderDTO
import com.jacksondeng.gojek.common.model.dto.RepoDTO
import com.jacksondeng.gojek.common.model.entity.Repo
import com.jacksondeng.gojek.githubcoroutine.data.api.FetchRepositoriesApi
import com.jacksondeng.gojek.githubcoroutine.data.repo.FetchRepositoriesRepoImpl
import com.jacksondeng.gojek.githubcoroutine.data.repo.TAG_LAST_CACHED_TIME
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*

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
    }

    @After
    fun teardown() {

    }

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Test
    internal fun `fetch repo success test`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery { api.fetchRepositories() } returns dummyResponse
            val data = repo.fetchRepositories(coroutinesTestRule.testDispatcherProvider)
            Assert.assertTrue(
                data == listOf(
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
        }
    }


    @Test
    internal fun `api failed, fallback to cache value test`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery { api.fetchRepositories() } coAnswers {
                error(
                    NetworkErrorException()
                )
            }

            coEvery { sharePref.getLong(TAG_LAST_CACHED_TIME, -1L) } returns 1234L

            val mockResponse = dummyResponse.map {
                repo.mapToModel(it)
            }

            coEvery { repoDao.getCachedReposSuspend() } returns (
                    mockResponse
                    )

            val data = repo.fetchRepositories(coroutinesTestRule.testDispatcherProvider)
            Assert.assertTrue(data.isNotEmpty())
            Assert.assertTrue(data == mockResponse)
        }
    }

    @Test
    internal fun `api failed, cache is empty test`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery { api.fetchRepositories() } coAnswers {
                error(
                    NetworkErrorException()
                )
            }

            coEvery { sharePref.getLong(TAG_LAST_CACHED_TIME, -1L) } returns -1L

            coEvery { repoDao.getCachedReposSuspend() } coAnswers { emptyList() }

            val data = repo.fetchRepositories(coroutinesTestRule.testDispatcherProvider)
            Assert.assertTrue(data.isNullOrEmpty())
        }
    }
}