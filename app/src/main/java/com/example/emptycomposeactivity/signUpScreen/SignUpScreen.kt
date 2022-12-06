package com.example.emptycomposeactivity.signUpScreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.emptycomposeactivity.R
import com.example.emptycomposeactivity.Screens
import java.util.*


@Composable
fun SignUpScreen(navController: NavController) {
    val signUpViewModel: SignUpViewModel = viewModel()

    val signUpLogin: String by signUpViewModel.login.observeAsState("")
    val signUpEmail: String by signUpViewModel.email.observeAsState("")
    val signUpName: String by signUpViewModel.name.observeAsState("")
    val signUpPassword: String by signUpViewModel.password.observeAsState("")
    val signUpConfirmPassword: String by signUpViewModel.confirmPassword.observeAsState("")
    val signUpDateOfBirth: String by signUpViewModel.dateOfBirth.observeAsState("")

    val signUpManIsPressed: Boolean by signUpViewModel.manIsPressed.observeAsState(false)
    val signUpWomanIsPressed: Boolean by signUpViewModel.womanIsPressed.observeAsState(false)

    val equality: Boolean by signUpViewModel.equality.observeAsState(true)
    val correct: Boolean by signUpViewModel.correct.observeAsState(true)

    val signUpAllFieldsFilled: Boolean by signUpViewModel.allFieldsFilled.observeAsState(false)

    Column() {
        SignUpLogo()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
            SignUpRegistrationText()
            SignUpLoginField(login = signUpLogin) { signUpViewModel.onLoginChange(it) }
            SignUpEMailField(email = signUpEmail, isValid = correct) { signUpViewModel.onEmailChange(it) }
            SignUpNameField(name = signUpName) { signUpViewModel.onNameChange(it)}
            SignUpPasswordField(password = signUpPassword) { signUpViewModel.onPasswordChange(it) }
            SignUpConfirmPasswordField(confirmPassword = signUpConfirmPassword, equal = equality)
            { signUpViewModel.onConfirmPasswordChange(it) }

            SignUpDateOfBirthField(dateOfBirth = signUpDateOfBirth)
            { signUpViewModel.showDatePickerDialog(it) }

            Gender(selectedWoman = signUpWomanIsPressed, selectedMan = signUpManIsPressed)
            { signUpViewModel.buttonGenderIsPressed(it)}

            Column(
                modifier = Modifier.fillMaxWidth(),
            )
            {
                SignUpRegister(fieldsFilled = signUpAllFieldsFilled) { signUpViewModel.register() }
                SignUpIHaveAcc(navController)
            }
        }
    }
}

@Composable
fun SignUpLogo(){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var animationAlreadyStarted by remember {mutableStateOf(false)}

        val animatedHeightDp: Dp by animateDpAsState(
            targetValue = if (animationAlreadyStarted) 100.dp else 170.dp,
            animationSpec = tween (durationMillis = 500))
        val animatedWidthDp: Dp by animateDpAsState(
            targetValue = if (animationAlreadyStarted) 170.dp else 250.dp,
            animationSpec = tween (durationMillis = 500))

        LaunchedEffect(animationAlreadyStarted){
            animationAlreadyStarted = true
        }

        Image(
            painter = painterResource(R.drawable.mini_logo_movie_catalog),
            contentDescription = "Logo on Sign-Up Screen",
            modifier = Modifier
                .padding(0.dp, 56.dp, 0.dp, 8.dp)
                .height(animatedHeightDp)
                .width((animatedWidthDp))
        )
    }
}

@Composable
fun SignUpRegistrationText(){
    Text(
        "Регистрация",
        color = colorResource(R.color.dark_red),
        fontSize = 24.sp,
        textAlign = TextAlign.Left,
        modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp)
    )
}

@Composable
fun SignUpLoginField(login: String, onLoginChange: (String) -> Unit) {
    OutlinedTextField(value = login,
        onValueChange = onLoginChange,
        modifier = Modifier
            .padding(16.dp, 16.dp, 16.dp, 8.dp)
            .fillMaxWidth(),
        placeholder = { Text("Логин") },
        enabled = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colorResource(R.color.gray),
            placeholderColor = colorResource(R.color.gray_faded),
            cursorColor = colorResource(R.color.dark_red),
            textColor = colorResource(R.color.dark_red),
            focusedBorderColor = colorResource(R.color.dark_red)
        )
    )
}

@Composable
fun SignUpEMailField(email: String, isValid: Boolean, onEmailChange: (String) -> Unit) {
    OutlinedTextField(value = email,
        onValueChange = onEmailChange,
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth(),
        placeholder = { Text("E-mail") },
        enabled = true,
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        trailingIcon = { if(!isValid) Icon(Icons.Default.Close,
            contentDescription = "Иконка ошибки", tint = colorResource(R.color.white))},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colorResource(R.color.gray),
            placeholderColor = colorResource(R.color.gray_faded),
            cursorColor = colorResource(R.color.dark_red),
            textColor = colorResource(R.color.dark_red),
            focusedBorderColor = colorResource(R.color.dark_red)
        )
    )
}

@Composable
fun SignUpNameField(name: String, onNameChange: (String) -> Unit) {
    OutlinedTextField(value = name,
        onValueChange = onNameChange,
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth(),
        placeholder = { Text("Имя") },
        enabled = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colorResource(R.color.gray),
            placeholderColor = colorResource(R.color.gray_faded),
            cursorColor = colorResource(R.color.dark_red),
            textColor = colorResource(R.color.dark_red),
            focusedBorderColor = colorResource(R.color.dark_red)
        )
    )
}

@Composable
fun SignUpPasswordField(password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth(),
        placeholder = { Text("Пароль") },
        enabled = true,
        shape = RoundedCornerShape(8.dp),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colorResource(R.color.gray),
            placeholderColor = colorResource(R.color.gray_faded),
            cursorColor = colorResource(R.color.dark_red),
            textColor = colorResource(R.color.dark_red),
            focusedBorderColor = colorResource(R.color.dark_red)
        )
    )
}

@Composable
fun SignUpConfirmPasswordField(confirmPassword: String, equal: Boolean, onConfirmPasswordChange: (String) -> Unit) {
    OutlinedTextField(value = confirmPassword,
        onValueChange = onConfirmPasswordChange,
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth(),
        placeholder = { Text("Подтвердите пароль") },
        enabled = true,
        shape = RoundedCornerShape(8.dp),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        trailingIcon = { if(!equal)
            Icon(Icons.Default.Close,
            contentDescription = "Иконка ошибки", tint = colorResource(R.color.dark_red)) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colorResource(R.color.gray),
            placeholderColor = colorResource(R.color.gray_faded),
            cursorColor = colorResource(R.color.dark_red),
            textColor = colorResource(R.color.dark_red),
            focusedBorderColor = colorResource(R.color.dark_red)
        )
    )
}

@SuppressLint("ResourceType")
@Composable
fun SignUpDateOfBirthField(dateOfBirth: String, showDatePickerDialog: (Context) -> Unit) {
    val mContext = LocalContext.current

    OutlinedTextField(
        value = dateOfBirth,
        onValueChange = {},
        modifier = Modifier
            .padding(16.dp, 8.dp, 16.dp, 16.dp)
            .fillMaxWidth(),
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            showDatePickerDialog(mContext)
                        }
                    }
                }
            },
        readOnly = true,
        placeholder = { Text("Дата рождения") },
        trailingIcon = { Icon(painter = painterResource(R.drawable.ic_calendar),
            contentDescription = "Иконка для открытия календаря",
            tint = colorResource(R.color.white))},
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colorResource(R.color.gray),
            placeholderColor = colorResource(R.color.gray_faded),
            cursorColor = colorResource(R.color.dark_red),
            textColor = colorResource(R.color.dark_red),
            focusedBorderColor = colorResource(R.color.dark_red)
        )
    )
}

@Composable
fun Gender(selectedWoman: Boolean, selectedMan: Boolean, buttonGenderIsPressed: (Int) -> Unit){

    val womanBack = if (selectedWoman) colorResource(R.color.dark_red) else colorResource(R.color.black)
    val manBack = if (selectedMan) colorResource(R.color.dark_red) else colorResource(R.color.black)

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Min)
        .padding(16.dp, 0.dp)
        .border(1.dp, colorResource(R.color.white), RoundedCornerShape(8.dp))) {
        Button(
            onClick = { buttonGenderIsPressed(1) },
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = manBack,
                contentColor = colorResource(R.color.white)
            ),
            shape = RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp),
        )
        {
            Text("Мужчина", fontSize = 14.sp, textAlign = TextAlign.Center)
        }

        Divider(
            color = colorResource(R.color.white),
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )

        Button(
            onClick = { buttonGenderIsPressed(2) },
            shape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = womanBack,
                contentColor = colorResource(R.color.white)
            ),
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        )
        {
            Text("Женщина", fontSize = 14.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun SignUpRegister(fieldsFilled: Boolean, register: () -> Unit){

    val colorBorder =
        if (fieldsFilled) colorResource(R.color.dark_red)
        else colorResource(R.color.gray_faded)

    Button(
        enabled = fieldsFilled,
        onClick = { register() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.dark_red),
            contentColor = colorResource(R.color.white),
            disabledBackgroundColor = colorResource(R.color.black),
            disabledContentColor = colorResource(R.color.dark_red)
        ),
        border = BorderStroke(1.dp, colorBorder),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 32.dp, 16.dp, 4.dp)
            .height(44.dp)
    )
    {
        Text("Зарегистрироваться", fontSize = 16.sp)
    }
}

@Composable
fun SignUpIHaveAcc(navController: NavController) {
    Button(
        onClick = { navController.navigate(Screens.SignInScreen.route) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.black),
            contentColor = colorResource(R.color.dark_red)
        ),
        modifier = Modifier
            .padding(16.dp, 4.dp, 16.dp, 16.dp)
            .fillMaxWidth(),
    )
    {
        Text("У меня уже есть аккаунт", fontSize = 16.sp)
    }
}


