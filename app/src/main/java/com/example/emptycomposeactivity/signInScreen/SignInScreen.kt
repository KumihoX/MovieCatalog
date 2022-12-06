package com.example.emptycomposeactivity.signInScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.emptycomposeactivity.R
import com.example.emptycomposeactivity.Screens

@Composable
fun SignInScreen(navController: NavHostController) {

    val signInViewModel: SignInViewModel = viewModel()

    val signInLogin: String by signInViewModel.login.observeAsState("")
    val signInPassword: String by signInViewModel.password.observeAsState("")

    val signInAllFieldsFilled: Boolean by signInViewModel.allFieldsFilled.observeAsState(false)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        SignInLogo()
        LoginField(login = signInLogin) { signInViewModel.onLoginChange(it) }
        PasswordField(password = signInPassword) { signInViewModel.onPasswordChange(it) }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    )
    {
        SignInComeIn(fieldsFilled = signInAllFieldsFilled) { signInViewModel.comeIn() }
        SignInRegister(navController = navController)
    }
}

@Composable
fun SignInLogo(){
    Image(
        painter = painterResource(R.drawable.logo_movie_catalog),
        contentDescription = "Logo on Sign-In Screen",
        modifier = Modifier.padding(0.dp, 56.dp, 0.dp, 26.795.dp)
    )
}

@Composable
fun LoginField(login: String, onLoginChange: (String) -> Unit) {
    OutlinedTextField(
        value = login,
        onValueChange = onLoginChange,
        modifier = Modifier
            .padding(16.dp, 7.205.dp)
            .fillMaxWidth(),
        placeholder = { Text("Логин") },
        enabled = true,
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colorResource(R.color.gray),
            placeholderColor = colorResource(R.color.gray_faded),
            cursorColor = colorResource(R.color.dark_red),
            textColor = colorResource(R.color.dark_red),
            focusedBorderColor = colorResource(R.color.gray)
        )
    )
}

@Composable
fun PasswordField(password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier
            .padding(16.dp, 7.205.dp)
            .fillMaxWidth(),
        placeholder = { Text("Пароль") },
        enabled = true,
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colorResource(R.color.gray),
            placeholderColor = colorResource(R.color.gray_faded),
            cursorColor = colorResource(R.color.dark_red),
            textColor = colorResource(R.color.dark_red),
            focusedBorderColor = colorResource(R.color.gray)
        )
    )
}

@Composable
fun SignInComeIn(fieldsFilled: Boolean, comeIn: () -> Unit){

    val colorBorder =
        if (fieldsFilled) colorResource(R.color.dark_red)
        else colorResource(R.color.gray_faded)

    Button(
        onClick = { comeIn() },
        enabled = fieldsFilled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.dark_red),
            contentColor = colorResource(R.color.white),
            disabledBackgroundColor = colorResource(R.color.black),
            disabledContentColor = colorResource(R.color.dark_red)
        ),
        border = BorderStroke(1.dp, colorBorder),
        modifier = Modifier
            .height(44.dp)
            .padding(16.dp, 0.dp)
            .fillMaxWidth()
    )
    {
        Text("Войти", fontSize = 16.sp)
    }
}

@Composable
fun SignInRegister(navController: NavController){
    Button(
        onClick = {navController.navigate(Screens.SignUpScreen.route)},
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.black),
            contentColor = colorResource(R.color.dark_red)
        ),
        modifier = Modifier
            .padding(16.dp, 8.dp, 16.dp, 16.dp)
            .fillMaxWidth()
    )
    {
        Text("Регистрация", fontSize = 16.sp)
    }
}
