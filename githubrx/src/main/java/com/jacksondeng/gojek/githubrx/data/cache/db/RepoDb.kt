package com.jacksondeng.gojek.githubrx.data.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jacksondeng.gojek.common.model.entity.Repo
import com.jacksondeng.gojek.githubrx.data.cache.dao.RepoDao

const val DB_NAME = "ReposDB"
const val DB_VERSION = 1
const val TABLE_NAME_REPOS = "ReposTable"

@Database(
    entities = [Repo::class],
    exportSchema = false,
    version = DB_VERSION
)
abstract class RepoDb : RoomDatabase() {

    abstract fun repoDao(): RepoDao
}