package com.jacksondeng.gojek.popularrepositories.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jacksondeng.gojek.popularrepositories.data.repo.FetchRepositoriesRepo
import com.jacksondeng.gojek.popularrepositories.model.entity.Repo
import com.jacksondeng.gojek.popularrepositories.util.BaseSchedulerProvider
import com.jacksondeng.gojek.popularrepositories.util.SchedulerProvider
import com.jacksondeng.gojek.popularrepositories.util.State
import com.jacksondeng.gojek.popularrepositories.views.adapter.InteractionListener
import io.reactivex.disposables.CompositeDisposable

class FetchRepositoriesViewModel(private val repo: FetchRepositoriesRepo) : ViewModel(), InteractionListener {
    private var _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private var compositeDisposable = CompositeDisposable()

    private var repositories = listOf<Repo>()

    val isLoading = Transformations.map(state) {
        it is State.Loading
    }

    fun fetchRepositories(schedulerProvider: BaseSchedulerProvider = SchedulerProvider()) {
        _state.value = State.Loading()
        compositeDisposable.add(
            repo.fetchRepositories(schedulerProvider)
                .subscribe({ repos ->
                    repos?.let {
                        if (it.isNotEmpty()) {
                            repositories = it
                            _state.value = State.Loaded(it)
                        } else {
                            // TODO: localize strings
                            _state.value = State.Error("Failed to load more repositories")
                        }
                    } ?: run {
                        _state.value = State.Error("Unknown error occurred")
                    }
                }, {

                    _state.value = State.Error("Unknown error occurred")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    override fun onItemClicked(repo: Repo) {
        // TODO : Show drop down
    }

    fun onRefresh() {
        compositeDisposable.clear()
        fetchRepositories()
    }
}
