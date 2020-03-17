package viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jacksondeng.gojek.common.model.entity.Repo
import io.reactivex.disposables.CompositeDisposable
import util.State

class BaseViewModel : ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private var compositeDisposable = CompositeDisposable()

    private var repositories = mutableListOf<Repo>()

    val isLoading = Transformations.map(state) {
        it is State.Loading
    }

    val showEmptyLayout = Transformations.map(state) {
        it is State.Error && repositories.isEmpty()
    }

}