package com.example.emptycomposeactivity

sealed class Screens(val route: String) {
    object SignInScreen: Screens("sign_in_screen")
    object SignUpScreen: Screens("sign_up_screen")
}
