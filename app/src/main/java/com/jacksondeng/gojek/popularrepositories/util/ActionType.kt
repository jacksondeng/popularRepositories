package com.jacksondeng.gojek.popularrepositories.util

sealed class EventType {
    data class Expand(val position: Int): EventType()
    data class Collapse(val position: Int): EventType()
}