package github.crisacm.composepaging3.presentation.home

import github.crisacm.composepaging3.ui.base.ViewEvent
import github.crisacm.composepaging3.ui.base.ViewState

object HomeContracts {

  data class HomeState(
    val searching: Boolean = false,
  ) : ViewState

  sealed interface HomeEvent : ViewEvent {
    data class Search(val username: String) : HomeEvent
    data object Clear : HomeEvent
  }
}