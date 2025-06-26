package com.example.myapplication.navigator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.ui.product.screens.HomeScreen
import com.example.myapplication.ui.product.screens.DetailsScreen
import com.example.myapplication.ui.theme.product.cart.CartScreen
import com.example.myapplication.ui.theme.product.ProductViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.ui.theme.auth.LoginScreen
import com.example.myapplication.ui.theme.auth.RegisterScreen
import com.example.myapplication.ui.theme.favorites.FavoriteProductsScreen
import com.example.myapplication.ui.theme.favorites.PromoScreen
import com.example.myapplication.ui.theme.product.cart.OrderFormScreen
import com.example.myapplication.ui.theme.product.cart.ConfirmationScreen
import com.example.myapplication.ui.theme.product.screens.SplashScreen

@Composable
fun AppNavigation(
    viewModel: ProductViewModel = hiltViewModel(),
    onLanguageSelected: (String) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        // Splash screen
        composable("splash") {
            SplashScreen(
                onTimeout = {
                    navController.popBackStack()
                    navController.navigate("product")
                }
            )
        }

        composable("product") {
            HomeScreen(
                viewModel = viewModel,
                onProductClick = { navController.navigate("details/$it") },
                navController = navController,
                onLanguageSelected = onLanguageSelected
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
                navController = navController,
                onLanguageSelected = onLanguageSelected
            )
        }


        composable("favorites") {
            FavoriteProductsScreen(
                viewModel = viewModel,
                onProductClick = { product -> navController.navigate("details/${product.id}") },
                navController = navController,
                onLanguageSelected = onLanguageSelected
            )
        }

        // Cart screen
        composable("cart") {
            CartScreen(
                viewModel = viewModel,
                navController = navController,
                onLanguageSelected = onLanguageSelected,
                onBack = { navController.popBackStack() }
            )
        }

        // Order form screen
        composable("orderForm") {
            OrderFormScreen(
                navController = navController,
                viewModel = viewModel,
                onLanguageSelected = onLanguageSelected
            )
        }

        // Confirmation screen
        composable("confirmation") {
            ConfirmationScreen(
                navController = navController,
                onLanguageSelected = onLanguageSelected,
                viewModel = viewModel
            )
        }

        // Profile screen
        composable("profile") {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Page Profil")
            }
        }

        // Login screen
        composable("login") {
            LoginScreen(
                navController = navController,
                cartItemCount = 0,
                onLanguageSelected = onLanguageSelected,
                onLoginSuccess = { navController.navigate("orderForm") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        // Register screen
        composable("register") {
            RegisterScreen(
                navController = navController,
                onLanguageSelected = onLanguageSelected,
                cartItemCount = 0,
                onRegisterSuccess = { navController.popBackStack() }
            )
        }

        // Promo screen (كيحتاج onLanguageSelected)
        composable("promo") {
            PromoScreen(
                viewModel = viewModel,
                navController = navController,
                onProductClick = { product -> navController.navigate("details/${product.id}") },
                onLanguageSelected = onLanguageSelected
            )
        }
    }
}
