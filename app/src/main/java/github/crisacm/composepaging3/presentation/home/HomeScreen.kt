@file:OptIn(ExperimentalMaterial3Api::class)

package github.crisacm.composepaging3.presentation.home

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.twotone.Folder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import github.crisacm.composepaging3.R
import github.crisacm.composepaging3.domain.model.GitRepo
import github.crisacm.composepaging3.ui.theme.ComposePaging3Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
  data: Flow<PagingData<GitRepo>>?,
  state: HomeContracts.HomeState,
  onEvent: (event: HomeContracts.HomeEvent) -> Unit = {}
) {
  val focusRequester = remember { FocusRequester() }
  val keyboardController = LocalSoftwareKeyboardController.current

  val snackbarHostState = remember { SnackbarHostState() }
  val username = remember { mutableStateOf("") }

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) },
    modifier = Modifier
  ) { paddingValues ->
    Column(modifier = Modifier.padding(paddingValues)) {
      /** New view! */
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          modifier = Modifier.size(120.dp),
          painter = painterResource(R.drawable.ic_github),
          contentDescription = "Github icon"
        )
        Text(
          modifier = Modifier.padding(end = 24.dp),
          fontSize = 22.sp,
          text = "Let's Explore \nGitHub Repositories"
        )
      }
      Box(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), contentAlignment = Alignment.Center) {
        OutlinedTextField(
          modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 10.dp)
            .focusRequester(focusRequester),
          value = username.value,
          onValueChange = { username.value = it },
          keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
          keyboardActions = KeyboardActions(
            onSearch = {
              onEvent(HomeContracts.HomeEvent.Search(username.value))
              keyboardController?.hide()
            }
          ),
          maxLines = 1,
          trailingIcon = {
            androidx.compose.animation.AnimatedVisibility(
              visible = username.value.isNotEmpty(),
              enter = fadeIn(),
              exit = fadeOut()
            ) {
              IconButton(onClick = {
                onEvent(HomeContracts.HomeEvent.Clear)
                username.value = ""
                focusRequester.requestFocus()
                keyboardController?.show()
              }) {
                Icon(
                  imageVector = Icons.Rounded.Clear,
                  contentDescription = "Clear icon"
                )
              }
            }
          },
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Search,
              contentDescription = "Search icon"
            )
          },
          shape = RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp, topEnd = 50.dp, bottomEnd = 50.dp),
          placeholder = { Text("Type username") },
        )
      }
      // Initial state when not are enter an username
      if (data == null) {
        Text(
          modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, top = 36.dp)
            .align(Alignment.CenterHorizontally),
          text = "Enter the user to see a bit of code! 👻"
        )
      } else {
        /** Start loading data and manage states */
        val listItems = data.collectAsLazyPagingItems()
        LazyColumn {
          items(
            count = listItems.itemCount,
            key = listItems.itemKey { it.id }
          ) { index ->
            val repo = listItems[index]
            Row(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 10.dp, bottom = 10.dp)) {
              Icon(Icons.TwoTone.Folder, null)
              Text(
                modifier = Modifier.padding(start = 24.dp),
                text = repo?.name ?: "NAME"
              )
            }
            HorizontalDivider()
          }
          if (state.searching) {
            if (listItems.loadState.refresh is LoadState.NotLoading && listItems.itemCount <= 0) {
              item {
                Box(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 36.dp)
                    .align(Alignment.CenterHorizontally),
                  contentAlignment = Alignment.Center
                ) {
                  Text("Not are data to show 🤷🏻‍♂️")
                }
              }
            }
            when (val loadState = listItems.loadState.refresh) {
              is LoadState.Error -> {
                loadState.error.printStackTrace()
                item {
                  Column(
                    modifier = Modifier
                      .fillMaxWidth()
                      .padding(start = 24.dp, end = 24.dp, top = 36.dp)
                      .align(Alignment.CenterHorizontally)
                  ) {
                    Text(
                      modifier = Modifier.align(Alignment.CenterHorizontally),
                      fontSize = 22.sp,
                      color = Color.Black,
                      textAlign = TextAlign.Center,
                      text = "Ops! something not are working right.."
                    )
                    Text(
                      modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp),
                      text = loadState.error.message ?: "Unexpected error"
                    )
                  }
                }
              }

              LoadState.Loading -> {
                item {
                  Box(
                    modifier = Modifier
                      .fillMaxWidth()
                      .padding(start = 24.dp, end = 24.dp, top = 36.dp)
                      .align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center
                  ) {
                    CircularProgressIndicator()
                  }
                }
              }

              is LoadState.NotLoading -> Unit
            }
            when (val loadState = listItems.loadState.append) {
              is LoadState.Error -> {
                item {
                  Text("Cant load more data: ${loadState.error.message}")
                }
              }

              LoadState.Loading -> {
                item {
                  CircularProgressIndicator()
                }
              }

              is LoadState.NotLoading -> Unit
            }
          }
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
  ComposePaging3Theme {
    HomeScreen(
      data = flowOf(PagingData.from(listOf(GitRepo("1", "name", "description")))),
      state = HomeContracts.HomeState(searching = false)
    )
  }
}