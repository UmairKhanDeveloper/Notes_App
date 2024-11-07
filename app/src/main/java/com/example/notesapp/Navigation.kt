package com.example.notesapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination =Screens.HomeScreen.router) {
        composable(Screens.HomeScreen.router){
            HomeScreen(navController)
        }
        composable(Screens.DetailsScreen.router){
            DetailsScreen(navController)
        }

    }

}

sealed class Screens(val router: String) {
    object HomeScreen:Screens("HomeScreen")
    object DetailsScreen:Screens("DetailsScreen")
}