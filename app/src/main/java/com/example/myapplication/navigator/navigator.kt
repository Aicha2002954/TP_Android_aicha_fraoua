package com.example.myapplication.navigator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.navigation.NavController
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
fun AppNavigation(viewModel: ProductViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        // Splash screen (logo d'accueil)
        composable("splash") {
            SplashScreen(
                onTimeout = {
                    navController.popBackStack()
                    navController.navigate("product")
                }
            )
        }

        // Home product screen
        composable("product") {
            HomeScreen(
                viewModel = viewModel,
                onProductClick = { navController.navigate("details/$it") },
                navController = navController
            )
        }

        // Détails produit
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

        // Favoris
        composable("favorites") {
            FavoriteProductsScreen(
                viewModel = viewModel,
                onProductClick = { product ->
                    navController.navigate("details/${product.id}")
                },
                navController = navController
            )
        }

        // Panier
        composable("cart") {
            CartScreen(
                viewModel = viewModel,
                navController = navController,
                onBack = { navController.popBackStack() }
            )
        }

        // Formulaire de commande
        composable("orderForm") {
            OrderFormScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        // Confirmation de commande
        composable("confirmation") {
            ConfirmationScreen(
                navController = navController,
                viewModel = viewModel
            )
        }


        composable("profile") {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Page Profil")
            }
        }
        // Connexion
        composable("login") {
            LoginScreen(
                navController = navController,
                cartItemCount = 0,
                onLoginSuccess = { navController.navigate("orderForm") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                navController = navController,
                cartItemCount = 0,
                onRegisterSuccess = { navController.popBackStack() }
            )
        }

        //Promo
        composable("promo") {
            PromoScreen(
                viewModel = viewModel,
                navController = navController,
                onProductClick = { product -> navController.navigate("details/${product.id}") }
            )
        }

    }
}
