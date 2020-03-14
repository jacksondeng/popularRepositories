package com.jacksondeng.gojek.popularrepositories.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jacksondeng.gojek.popularrepositories.data.repo.FetchRepositoriesRepo
import com.jacksondeng.gojek.popularrepositories.util.BaseSchedulerProvider
import com.jacksondeng.gojek.popularrepositories.util.SchedulerProvider
import com.jacksondeng.gojek.popularrepositories.util.State
import io.reactivex.disposables.CompositeDisposable

class FetchRepositoriesViewModel(private val repo: FetchRepositoriesRepo) : ViewModel() {
    private var _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private var compositeDisposable = CompositeDisposable()

    fun fetchRepositories(schedulerProvider: BaseSchedulerProvider = SchedulerProvider()) {
        compositeDisposable.add(
            repo.fetchRepositories(schedulerProvider)
                .subscribe({ repos ->
                    repos?.let {
                        _state.value = State.Loaded(it)
                    } ?: run {
                        _state.value = State.Error()
                    }
                }, {
                    _state.value = State.Error()
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
