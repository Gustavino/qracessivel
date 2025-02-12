package br.usp.qracessivel.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.ui.components.MainScreen
import br.usp.qracessivel.ui.components.PermissionRequest
import br.usp.qracessivel.ui.result.ResultScreen
import br.usp.qracessivel.viewmodel.MainViewModel

sealed class Screen(val route: String) {
    data object Permission : Screen("permission")
    data object Scanner : Screen("scanner")
    data object Result : Screen("result")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    onGalleryClick: () -> Unit,
    hasCameraPermission: Boolean,
    onRequestPermission: () -> Unit,
    viewModel: MainViewModel,
    startDestination: String = if (hasCameraPermission) Screen.Scanner.route else Screen.Permission.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Permission.route) {
            PermissionRequest(
                onRequestPermission = {
                    onRequestPermission()
                    navController.navigate(Screen.Scanner.route) {
                        popUpTo(Screen.Permission.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Scanner.route) {
            MainScreen(
                viewModel = viewModel,
                onGalleryClick = onGalleryClick,
                onQrDetected = { content ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "qrContent",
                        value = content
                    )
                    navController.navigate(Screen.Result.route)
                }
            )
        }

        composable(Screen.Result.route) {
            val content = navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<ResultContent>("qrContent")
            Log.d("NavGraph", "AppNavigation: $content")
            content?.let {
                ResultScreen(
                    content = it,
                    onDismiss = { navController.popBackStack() }
                )
            }
        }
    }
}