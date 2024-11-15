@file:OptIn(ExperimentalMaterial3Api::class)

package github.crisacm.composepaging3.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.material.icons.twotone.Folder
import androidx.compose.material.icons.twotone.Inbox
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material.icons.twotone.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
  state: HomeContracts.HomeState,
  onEvent: (event: HomeContracts.HomeEvent) -> Unit = {}
) {
  val snackbarHostState = remember { SnackbarHostState() }
  val context = LocalContext.current

  val searchActive = remember { mutableStateOf(false) }
  val searchText = remember { mutableStateOf("") }
  val searchHistory = remember { mutableStateListOf<String>() }

  val apiSelected = remember { mutableStateOf(true) }
  val localSelected = remember { mutableStateOf(false) }

  if (apiSelected.value) {
    ButtonDefaults.outlinedButtonColors(
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.surface
    )
  } else ButtonDefaults.outlinedButtonColors()

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) },
    topBar = {
      TopAppBar(
        title = { Text("Github Repositories") },
        actions = {
          Text(
            modifier = Modifier
              .clickable { onEvent(HomeContracts.HomeEvent.Clear) }
              .padding(end = 12.dp),
            text = "Clear"
          )
        }
      )
    },
    modifier = Modifier
  ) { paddingValues ->
    Column(modifier = Modifier.padding(paddingValues)) {
      SearchBar(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 10.dp, end = 10.dp),
        query = searchText.value,
        onQueryChange = { searchText.value = it },
        onSearch = {
          searchActive.value = false
          searchHistory.add(it)

          if (apiSelected.value) onEvent(HomeContracts.HomeEvent.Search(it))
          if (localSelected.value) onEvent(HomeContracts.HomeEvent.get(it))
        },
        active = searchActive.value,
        onActiveChange = { searchActive.value = it },
        placeholder = { Text(text = "Enter an username") },
        leadingIcon = {
          Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
        },
        trailingIcon = {
          if (searchActive.value) {
            Icon(
              modifier = Modifier.clickable {
                if (searchText.value.isNotEmpty()) {
                  searchText.value = ""
                } else {
                  searchActive.value = false
                }
              },
              imageVector = Icons.Default.Close,
              contentDescription = "Close icon"
            )
          }
        }
      ) {
        if (searchHistory.isNotEmpty()) {
          searchHistory.forEach {
            Row(modifier = Modifier.padding(12.dp)) {
              Icon(imageVector = Icons.Default.History, contentDescription = null)
              Spacer(modifier = Modifier.width(10.dp))
              Text(text = it)
            }
          }
        }
        Button(
          modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
          enabled = searchHistory.isNotEmpty(),
          onClick = { searchHistory.clear() }
        ) {
          Text(
            modifier = Modifier,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            text = "clear all history"
          )
        }
      }
      Row {
        OutlinedButton(
          onClick = {
            apiSelected.value = true
            localSelected.value = false
            onEvent(HomeContracts.HomeEvent.Clear)
          },
          modifier = Modifier
            .weight(1f)
            .padding(start = 10.dp, end = 5.dp, top = 8.dp),
          shape = RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp, topEnd = 6.dp, bottomEnd = 6.dp),
          colors = if (apiSelected.value) {
            ButtonDefaults.outlinedButtonColors(
              containerColor = MaterialTheme.colorScheme.primary,
              contentColor = MaterialTheme.colorScheme.surface
            )
          } else ButtonDefaults.outlinedButtonColors()
        ) {
          Text(
            text = "API",
            style = MaterialTheme.typography.bodyMedium
          )
        }
        OutlinedButton(
          onClick = {
            apiSelected.value = false
            localSelected.value = true
            onEvent(HomeContracts.HomeEvent.Clear)
          },
          modifier = Modifier
            .weight(1f)
            .padding(start = 5.dp, end = 10.dp, top = 8.dp),
          shape = RoundedCornerShape(topStart = 6.dp, bottomStart = 6.dp, topEnd = 50.dp, bottomEnd = 50.dp),
          colors = if (localSelected.value) {
            ButtonDefaults.outlinedButtonColors(
              containerColor = MaterialTheme.colorScheme.primary,
              contentColor = MaterialTheme.colorScheme.surface
            )
          } else ButtonDefaults.outlinedButtonColors()
        ) {
          Text(
            text = "Local",
            style = MaterialTheme.typography.bodyMedium
          )
        }
      }
      Text(
        modifier = Modifier
          .padding(10.dp)
          .align(Alignment.CenterHorizontally),
        text = "Count ${state.data.size}"
      )
      when {
        state.loading == true -> {
          Box(
            modifier = Modifier
              .fillMaxHeight()
              .fillMaxWidth(),
            contentAlignment = Alignment.Center
          ) {
            CircularProgressIndicator()
          }
        }

        state.empty == true -> {
          Box(
            modifier = Modifier
              .fillMaxHeight()
              .fillMaxWidth(),
            contentAlignment = Alignment.Center
          ) {
            Column(
              modifier = Modifier,
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Icon(
                modifier = Modifier
                  .size(80.dp)
                  .alpha(.4f),
                imageVector = Icons.TwoTone.Inbox,
                contentDescription = "inbox icon"
              )
              Text(
                modifier = Modifier
                  .padding(start = 48.dp, end = 48.dp, top = 12.dp)
                  .alpha(.4f),
                fontSize = 24.sp,
                maxLines = 2,
                textAlign = TextAlign.Center,
                text = "Not results found"
              )
            }
          }
        }

        state.error == true && state.errorMsg != null -> {
          Box(
            modifier = Modifier
              .fillMaxHeight()
              .fillMaxWidth(),
            contentAlignment = Alignment.Center
          ) {
            Column(
              modifier = Modifier,
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Icon(
                modifier = Modifier
                  .size(80.dp)
                  .alpha(.4f),
                imageVector = Icons.TwoTone.Warning,
                contentDescription = "warning error"
              )
              Text(
                modifier = Modifier
                  .padding(start = 48.dp, end = 48.dp, top = 12.dp)
                  .alpha(.4f),
                fontSize = 24.sp,
                maxLines = 2,
                textAlign = TextAlign.Center,
                text = "Ops! Something went wrong."
              )
              Text(text = state.errorMsg)
            }
          }
        }

        state.data.isNotEmpty() -> {
          LazyColumn {
            items(state.data, key = { repo -> repo.id }) { repo ->
              Row(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 10.dp, bottom = 10.dp)) {
                Icon(Icons.TwoTone.Folder, null)
                Text(modifier = Modifier.padding(start = 24.dp), text = repo.id)
                Text(modifier = Modifier.padding(start = 24.dp), text = repo.name)
              }
              HorizontalDivider()
            }
          }
        }

        else -> {
          Box(
            modifier = Modifier
              .fillMaxHeight()
              .fillMaxWidth(),
            contentAlignment = Alignment.Center
          ) {
            Column(
              modifier = Modifier,
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Icon(
                modifier = Modifier
                  .size(80.dp)
                  .alpha(.4f),
                imageVector = Icons.TwoTone.Search,
                contentDescription = "search icon"
              )
              Text(
                modifier = Modifier
                  .padding(start = 48.dp, end = 48.dp, top = 12.dp)
                  .alpha(.4f),
                fontSize = 24.sp,
                maxLines = 2,
                textAlign = TextAlign.Center,
                text = "First make a search!"
              )
            }
          }
        }
      }
    }
  }
}
