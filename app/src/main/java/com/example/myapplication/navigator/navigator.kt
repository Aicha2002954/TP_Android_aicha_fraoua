package com.example.myapplication.navigator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.navigation.NavController
import com.example.myapplication.ui.product.screens.HomeScreen
import com.example.myapplication.ui.product.screens.DetailsScreen
import com.example.myapplication.ui.theme.auth.LoginScreen
import com.example.myapplication.ui.theme.auth.RegisterScreen
import com.example.myapplication.ui.theme.product.ProductViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.ui.theme.favorites.FavoriteProductsScreen

@Composable
fun AppNavigation(viewModel: ProductViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onProductClick = { navController.navigate("details/$it") },
                navController = navController
            )
        }

        composable(
            "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetailsScreen(
                productId = id,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                navController = navController
            )
        }

        composable("favorites") {
            FavoriteProductsScreen(
                viewModel = viewModel,
                onProductClick = { product ->
                    navController.navigate("details/${product.id}")
                }
            )
        }

        composable("cart") {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Page du Panier")
            }
        }

        composable("profile") {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Page Profil")
            }
        }


        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.popBackStack("login", inclusive = true)
                    navController.navigate("profile")
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }


        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.popBackStack("register", inclusive = true)
                    navController.navigate("profile")
                },
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }
    }
}
