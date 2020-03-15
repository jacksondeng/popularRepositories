package com.jacksondeng.gojek.popularrepositories

import android.accounts.NetworkErrorException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jacksondeng.gojek.popularrepositories.data.repo.FetchRepositoriesRepo
import com.jacksondeng.gojek.popularrepositories.model.entity.Repo
import com.jacksondeng.gojek.popularrepositories.viewmodel.FetchRepositoriesViewModel
import com.jacksondeng.gojek.popularrepositories.util.State
import com.jacksondeng.gojek.popularrepositories.util.TrampolineSchedulerProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import org.junit.*

class ViewmodelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private val repo = mockk<FetchRepositoriesRepo>(relaxed = true)

    lateinit var viewModel: FetchRepositoriesViewModel

    private val dummyResponse = listOf(
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


    @Before
    fun setUp() {
        MockKAnnotations.init(FetchRepositoriesViewModel::class)
        viewModel = FetchRepositoriesViewModel(repo)
    }

    @After
    fun tearDown() {

    }

    @Test
    internal fun `fetch repos success test`() {
        val schedulerProvider = TrampolineSchedulerProvider()
        every { repo.fetchRepositories(schedulerProvider) } returns Flowable.just(dummyResponse)
        viewModel.fetchRepositories(schedulerProvider)
        Assert.assertTrue(viewModel.state.getOrAwaitValue() == State.Loaded(dummyResponse))
    }

    @Test
    internal fun `fetch repos failed test`() {
        val schedulerProvider = TrampolineSchedulerProvider()
        every { repo.fetchRepositories(schedulerProvider) } returns Flowable.error(
            NetworkErrorException()
        )
        viewModel.fetchRepositories(schedulerProvider)
        Assert.assertTrue(viewModel.state.getOrAwaitValue() is State.Error)
    }

    @Test
    internal fun `fetch repos returns empty list test`() {
        val schedulerProvider = TrampolineSchedulerProvider()
        every { repo.fetchRepositories(schedulerProvider) } returns Flowable.just(emptyList())
        viewModel.fetchRepositories(schedulerProvider)
        Assert.assertTrue(viewModel.state.getOrAwaitValue() is State.Error)
    }
}