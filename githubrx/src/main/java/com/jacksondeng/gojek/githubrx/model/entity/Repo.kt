package com.jacksondeng.gojek.githubrx.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jacksondeng.gojek.githubrx.data.cache.db.TABLE_NAME_REPOS

@Entity(tableName = TABLE_NAME_REPOS)
data class Repo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String,
    val name: String,
    val avatarUrl: String,
    val repoUrl: String,
    val description: String,
    val language: String,
    val languageColor: String,
    val stars: Int,
    val forks: Int
) {
    override fun equals(other: Any?): Boolean {
        if (other is Repo) {
            return author == other.author && name == other.name && repoUrl == other.repoUrl &&
                    stars == other.stars && forks == other.forks
        }

        return false
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
