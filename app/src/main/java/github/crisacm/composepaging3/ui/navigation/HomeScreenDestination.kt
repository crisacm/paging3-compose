package github.crisacm.composepaging3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import github.crisacm.composepaging3.presentation.home.HomeScreen
import github.crisacm.composepaging3.presentation.home.HomeViewModel

@Composable
fun HomeScreenDestination() {
  val viewModel = hiltViewModel<HomeViewModel>()
  HomeScreen(
    state = viewModel.viewState.value,
    onEvent = viewModel::setEvent
  )
}
