package com.jacksondeng.gojek.popularrepositories.data.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jacksondeng.gojek.popularrepositories.data.cache.dao.RepoDao
import com.jacksondeng.gojek.popularrepositories.model.entity.Repo

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