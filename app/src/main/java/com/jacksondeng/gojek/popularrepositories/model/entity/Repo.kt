package com.jacksondeng.gojek.popularrepositories.model.entity

data class Repo(
    val author: String,
    val name: String,
    val avatarUrl: String,
    val repoUrl: String,
    val description: String,
    val language: String,
    val languageColor: String,
    val starts: Int,
    val forks: Int
)
