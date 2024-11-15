package github.crisacm.composepaging3.presentation.home

import github.crisacm.composepaging3.domain.model.GitRepo
import github.crisacm.composepaging3.ui.base.ViewEvent
import github.crisacm.composepaging3.ui.base.ViewState

object HomeContracts {

  data class HomeState(
    val data: List<GitRepo> = emptyList(),
    val loading: Boolean? = null,
    val empty: Boolean? = null,
    val error: Boolean? = null,
    val errorMsg: String? = null,
  ) : ViewState

  sealed interface HomeEvent : ViewEvent {
    data class Search(val username: String) : HomeEvent
    data class get(val username: String) : HomeEvent
    data object Clear : HomeEvent
  }
}