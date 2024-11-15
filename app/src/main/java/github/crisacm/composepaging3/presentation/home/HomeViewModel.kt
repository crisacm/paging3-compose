package github.crisacm.composepaging3.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import github.crisacm.composepaging3.domain.repository.GitRepoRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val gitRepoRepo: GitRepoRepo
) : ViewModel() {

  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

  private val _viewState: MutableState<HomeContracts.HomeState> = mutableStateOf(HomeContracts.HomeState())
  val viewState: State<HomeContracts.HomeState> = _viewState

  private val _event: MutableSharedFlow<HomeContracts.HomeEvent> = MutableSharedFlow()

  init {
    viewModelScope.launch {
      _event.collectLatest {
        when (it) {
          HomeContracts.HomeEvent.Clear -> clear()
          is HomeContracts.HomeEvent.Search -> search(it.username)
          is HomeContracts.HomeEvent.get -> get(it.username)
        }
      }
    }
  }

  fun setEvent(event: HomeContracts.HomeEvent) {
    setState { HomeContracts.HomeState(loading = true) }
    viewModelScope.launch { _event.emit(event) }
  }

  private fun setState(reducer: HomeContracts.HomeState.() -> HomeContracts.HomeState) {
    val newState = viewState.value.reducer()
    _viewState.value = newState
  }

  private fun clear() {
    viewModelScope.launch(ioDispatcher) {
      setState { HomeContracts.HomeState() }
    }
  }

  private fun search(username: String) {
    viewModelScope.launch(ioDispatcher) {
      gitRepoRepo.fetchRepositories(username)
        .onSuccess { setState { HomeContracts.HomeState(data = it) } }
        .onFailure { setState { HomeContracts.HomeState(empty = true) } }
    }
  }

  private fun get(username: String) {
    viewModelScope.launch(ioDispatcher) {
      gitRepoRepo.getRepositories(username)
        .onSuccess { setState { HomeContracts.HomeState(data = it) } }
        .onFailure { setState { HomeContracts.HomeState(empty = true) } }
    }
  }
}