package github.crisacm.composepaging3.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import github.crisacm.composepaging3.presentation.home.HomeContracts
import github.crisacm.composepaging3.presentation.home.HomeScreen
import github.crisacm.composepaging3.ui.navigation.Home
import github.crisacm.composepaging3.ui.navigation.Navigation
import github.crisacm.composepaging3.ui.theme.ComposePaging3Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      ComposePaging3Theme {
        Navigation(Home)
      }
    }
  }
}
