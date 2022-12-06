package com.example.emptycomposeactivity

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.emptycomposeactivity.signInScreen.SignInScreen
import com.example.emptycomposeactivity.signUpScreen.SignUpScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SignInScreen.route) {
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController = navController)
        }
        
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
    }
}
