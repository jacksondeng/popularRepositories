package com.jacksondeng.gojek.githubrx.util

import com.jacksondeng.gojek.common.model.entity.Repo

sealed class State {
    data class Loaded(val repositories: List<Repo>) : State()
    data class Loading(val message: String = "") : State()
    data class Error(val message: String = "") : State()
    data class Event(val type: EventType): State()
}