package com.jacksondeng.gojek.popularrepositories.model.entity

data class Repo(
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
