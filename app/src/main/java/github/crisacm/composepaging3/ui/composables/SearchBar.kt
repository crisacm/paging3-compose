@file:OptIn(ExperimentalMaterial3Api::class)

package github.crisacm.composepaging3.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomSearchBar() {
  val searchActive = remember { mutableStateOf(false) }
  val searchText = remember { mutableStateOf("") }
  val searchHistory = remember { mutableStateListOf<String>() }

  SearchBar(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 10.dp, end = 10.dp),
    query = searchText.value,
    onQueryChange = { searchText.value = it },
    onSearch = {
      searchActive.value = false
      searchHistory.add(it)
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
}