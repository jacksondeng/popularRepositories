package com.jacksondeng.gojek.popularrepositories.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jacksondeng.gojek.popularrepositories.data.cache.db.TABLE_NAME_REPOS
import com.jacksondeng.gojek.popularrepositories.model.entity.Repo
import io.reactivex.Single

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateCache(repos: List<Repo>)

    @Query("SELECT * FROM $TABLE_NAME_REPOS")
    fun getCachedRepos(): Single<List<Repo>>
}