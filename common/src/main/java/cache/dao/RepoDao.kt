package cache.dao

import androidx.room.*
import com.jacksondeng.gojek.common.model.entity.Repo
import cache.db.TABLE_NAME_REPOS
import io.reactivex.Single

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateCache(repos: List<Repo>)

    @Query("SELECT * FROM $TABLE_NAME_REPOS")
    fun getCachedRepos(): Single<List<Repo>>

    @Query("DELETE FROM $TABLE_NAME_REPOS")
    fun clearCache()

    // TODO : Separate DAO for githubcoroutines module and githubrx module
    @Query("SELECT * FROM $TABLE_NAME_REPOS")
    fun getCachedReposSuspend(): List<Repo>
}