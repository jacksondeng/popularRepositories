package com.jacksondeng.gojek.popularrepositories

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cache.dao.RepoDao
import cache.db.RepoDb
import com.jacksondeng.gojek.common.model.entity.Repo
import util.TrampolineSchedulerProvider
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoDbTest {

    private lateinit var db: RepoDb
    private lateinit var repoDao: RepoDao

    @Before
    internal fun setUp() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<PopularRepositoriesApplication>(),
            RepoDb::class.java
        )
            .allowMainThreadQueries()
            .build()
        repoDao = db.repoDao()
    }

    @After
    fun teardown() {
        RxJavaPlugins.reset()
        db.close()
    }

    val dummyResponse = listOf(
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
        ),
        Repo(
            author = "facebook",
            name = "pipemedia",
            avatarUrl = "https://github.com/facebook.png",
            repoUrl = "https://github.com/google/pipemedia",
            description = "pipemedia is a cross-platform framework for building multimodal applied machine learning pipelines",
            language = "C",
            languageColor = "#f34b7d",
            stars = 5641,
            forks = 977
        )
    )

    @Test
    internal fun insert_test() {
        repoDao.updateCache(dummyResponse)

        val observer = repoDao.getCachedRepos()
            .subscribeOn(TrampolineSchedulerProvider().io())
            .test()
            .assertValue { it == dummyResponse }

        observer.dispose()
    }

    @Test
    internal fun empty_db_test() {
        repoDao.clearCache()

        val observer = repoDao.getCachedRepos()
            .subscribeOn(TrampolineSchedulerProvider().io())
            .test()
            .assertValue {
                it.isEmpty()
            }
            .assertTerminated()

        observer.dispose()
    }

    @Test
    internal fun clear_cache_test() {
        repoDao.updateCache(dummyResponse)

        val observer = repoDao.getCachedRepos()
            .subscribeOn(TrampolineSchedulerProvider().io())
            .test()
            .assertValue {
                it.isNotEmpty()
            }

        repoDao.clearCache()
        empty_db_test()

        observer.dispose()
    }
}