package com.jacksondeng.gojek.common.model.dto

import com.squareup.moshi.Json

data class RepoDTO(
    val author: String,
    val name: String,
    @field:Json(name = "avatar")
    val avatarUrl: String?,
    @field:Json(name = "url")
    val repoUrl: String?,
    val description: String,
    val language: String?,
    val languageColor: String?,
    val stars: Int,
    val forks: Int,
    val currentPeriodStars: Int,
    @field:Json(name = "builtBy")
    val builer: List<BuilderDTO>
)
