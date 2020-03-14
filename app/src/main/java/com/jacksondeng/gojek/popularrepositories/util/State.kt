package com.jacksondeng.gojek.popularrepositories.util

import com.jacksondeng.gojek.popularrepositories.model.entity.Repo

sealed class State {
    data class Loaded(val repositories: List<Repo>) : State()
    data class Loading(val message: String = "") : State()
    data class Error(val message: String = "") : State()
    data class RefreshList(val repositories: List<Repo>) : State()
}