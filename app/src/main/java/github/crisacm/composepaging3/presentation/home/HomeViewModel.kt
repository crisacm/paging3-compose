package github.crisacm.composepaging3.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import github.crisacm.composepaging3.domain.model.GitRepo
import github.crisacm.composepaging3.domain.repository.GitRepoRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val gitRepoRepo: GitRepoRepo,
) : ViewModel() {

  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

  private val _viewState: MutableState<HomeContracts.HomeState> = mutableStateOf(HomeContracts.HomeState())
  val viewState: State<HomeContracts.HomeState> = _viewState

  private val _event: MutableSharedFlow<HomeContracts.HomeEvent> = MutableSharedFlow()

  var data: Flow<PagingData<GitRepo>>? = null

  init {
    viewModelScope.launch {
      _event.collectLatest {
        when (it) {
          HomeContracts.HomeEvent.Clear -> {
            setState { HomeContracts.HomeState(searching = false) }
            data = null
          }

          is HomeContracts.HomeEvent.Search -> {
            setState { HomeContracts.HomeState(searching = true) }
            data = gitRepoRepo.fetchRepositoriesPager(it.username).cachedIn(viewModelScope)
          }
        }
      }
    }
  }

  fun setEvent(event: HomeContracts.HomeEvent) {
    viewModelScope.launch { _event.emit(event) }
  }

  private fun setState(reducer: HomeContracts.HomeState.() -> HomeContracts.HomeState) {
    val newState = viewState.value.reducer()
    _viewState.value = newState
  }
}