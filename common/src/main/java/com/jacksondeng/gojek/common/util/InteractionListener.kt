package com.jacksondeng.gojek.common.util

interface InteractionListener {
    fun onItemClicked(expanded: Boolean, position: Int)
}