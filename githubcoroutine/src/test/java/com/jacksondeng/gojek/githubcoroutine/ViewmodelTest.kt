package com.jacksondeng.gojek.githubcoroutine

import com.jacksondeng.gojek.common.model.entity.Repo
import com.jacksondeng.gojek.common.util.State
import com.jacksondeng.gojek.githubcoroutine.data.repo.FetchRepositoriesRepo
import com.jacksondeng.gojek.githubcoroutine.viewmodel.FetchRepositoriesViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.*

class ViewmodelTest {
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private val repo = mockk<FetchRepositoriesRepo>(relaxed = true)

    lateinit var viewModel: FetchRepositoriesViewModel

    val dispatcher = Dispatchers.Unconfined

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
        coEvery { repo.fetchRepositories(coroutinesTestRule.testDispatcherProvider) } returns dummyResponse
        viewModel.fetchRepositories(dispatcher, dispatcher)
        Assert.assertTrue(viewModel.state.getOrAwaitValue() == State.Loaded(dummyResponse))
    }

    @Test
    internal fun `fetch repos returns empty list test`() {
        coEvery { repo.fetchRepositories(coroutinesTestRule.testDispatcherProvider) } returns emptyList()
        viewModel.fetchRepositories(dispatcher, dispatcher)
        Assert.assertTrue(viewModel.state.getOrAwaitValue() is State.Error)
    }
}