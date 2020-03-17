package com.jacksondeng.gojek.githubrx.model.dto

import com.squareup.moshi.Json

data class BuilderDTO(
    val username: String,
    @field:Json(name = "href")
    val githubProfileUrl: String,
    @field:Json(name = "avatar")
    val avatarUrl: String?
)