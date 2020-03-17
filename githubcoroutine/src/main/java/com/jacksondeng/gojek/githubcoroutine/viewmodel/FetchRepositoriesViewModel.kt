package com.jacksondeng.gojek.githubcoroutine.viewmodel

import androidx.lifecycle.*
import com.jacksondeng.gojek.common.model.entity.Repo
import com.jacksondeng.gojek.githubcoroutine.data.repo.FetchRepositoriesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.jacksondeng.gojek.common.util.EventType
import com.jacksondeng.gojek.common.util.InteractionListener
import com.jacksondeng.gojek.common.util.State
import javax.inject.Inject

class FetchRepositoriesViewModel @Inject constructor(private val repo: FetchRepositoriesRepo) :
    ViewModel(),
    InteractionListener {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private var repositories = mutableListOf<Repo>()

    val isLoading = Transformations.map(state) {
        it is State.Loading
    }

    val showEmptyLayout = Transformations.map(state) {
        it is State.Error && repositories.isEmpty()
    }

    fun fetchRepositories() {
        viewModelScope.launch {
            _state.value = State.Loading()
            var data = emptyList<Repo>()
            withContext(Dispatchers.IO) {
                data = repo.fetchRepositories()
            }
            if (data.isNotEmpty()) {
                repositories.addAll(data)
                _state.value = State.Loaded(data)
            } else {
                // TODO: localize strings
                _state.value = State.Error("Unknown error occurred")
            }
        }
    }

    override fun onItemClicked(expanded: Boolean, position: Int) {
        if (expanded) {
            _state.value = State.Event(EventType.Collapse(position))
        } else {
            _state.value = State.Event(EventType.Expand(position))
        }
    }

    fun onRefresh() {
        repositories.clear()
        fetchRepositories()
    }
}