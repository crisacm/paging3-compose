package github.crisacm.composepaging3.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

const val TRANSITION_DURACION = 500

@Composable
fun Navigation(startDestination: Any) {
  val navController = rememberNavController()

  NavHost(
    navController = navController,
    startDestination = startDestination,
    enterTransition = {
      fadeIn(
        animationSpec = tween(TRANSITION_DURACION)
      ) + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(TRANSITION_DURACION))
    },
    exitTransition = {
      fadeOut(
        animationSpec = tween(TRANSITION_DURACION)
      ) + slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(TRANSITION_DURACION))
    }
  ) {
    composable<Home> {
      HomeScreenDestination()
    }
  }
}

@Serializable
object Home
