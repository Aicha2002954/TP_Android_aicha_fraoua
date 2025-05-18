package com.example.myapplication.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.ui.product.screens.HomeScreen
import com.example.myapplication.ui.product.screens.DetailsScreen
import com.example.myapplication.ui.theme.product.ProductViewModel

@Composable
fun AppNavigation(viewModel: ProductViewModel = remember { ProductViewModel() }) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(viewModel = viewModel, onProductClick = {
                navController.navigate("details/$it")
            })
        }
        composable(
            "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetailsScreen(productId = id, viewModel = viewModel, onBack = { navController.popBackStack() })
        }
    }
}
