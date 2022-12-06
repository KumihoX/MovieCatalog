package com.example.emptycomposeactivity.signUpScreen

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emptycomposeactivity.network.auth.AuthRepository
import com.example.emptycomposeactivity.network.auth.LoginRequestBody
import com.example.emptycomposeactivity.network.auth.RegisterRequestBody
import com.example.emptycomposeactivity.network.favoriteMovies.FavoriteMoviesRepository
import com.example.emptycomposeactivity.R
import kotlinx.coroutines.launch
import java.util.*

class SignUpViewModel: ViewModel() {

    private val _login = MutableLiveData("")
    var login: LiveData<String> = _login

    private val _email = MutableLiveData("")
    var email: LiveData<String> = _email

    private val _name = MutableLiveData("")
    var name: LiveData<String> = _name

    private val _password = MutableLiveData("")
    var password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData("")
    var confirmPassword: LiveData<String> = _confirmPassword

    private val _dateOfBirth = MutableLiveData("")
    var dateOfBirth: LiveData<String> = _dateOfBirth

    private val _manIsPressed = MutableLiveData(false)
    var manIsPressed: LiveData<Boolean> = _manIsPressed

    private val _womanIsPressed = MutableLiveData(false)
    var womanIsPressed: LiveData<Boolean> = _womanIsPressed

    private var _gender: Boolean = false

    private val _equality = MutableLiveData(true)
    var equality: LiveData<Boolean> = _equality

    private val _correct = MutableLiveData(true)
    var correct: LiveData<Boolean> = _correct

    private val _allFieldsFilled = MutableLiveData(false)
    var allFieldsFilled: LiveData<Boolean> = _allFieldsFilled

    private fun passwordComparison(){
        _equality.value = _password.value == _confirmPassword.value
    }

    private fun correctEmail(){
        _correct.value = _email.value?.let { android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() }
    }

    private fun checkGender(){
        if (_manIsPressed.value == true || _womanIsPressed.value == true){
            _gender = true
        }
    }

    private fun checkFields() {
        val login = _login.value
        val email = _email.value
        val name = _name.value
        val password = _password.value
        val confirmPassword = _confirmPassword.value
        val dateOfBirth = _dateOfBirth.value
        checkGender()
        val gender = _gender

        if (!login.isNullOrEmpty() && !email.isNullOrEmpty()
            && !name.isNullOrEmpty() && !password.isNullOrEmpty()
            && !confirmPassword.isNullOrEmpty() && !dateOfBirth.isNullOrEmpty()
            && gender) {
            _allFieldsFilled.value = _equality.value == _correct.value ==  true
        }
        else
            _allFieldsFilled.value = false
    }

    fun onLoginChange(newLogin: String){
        _login.value = newLogin
        checkFields()
    }

    fun onEmailChange(newEmail: String){
        _email.value = newEmail
        correctEmail()
        checkFields()
    }

    fun onNameChange(newName: String){
        _name.value = newName
        checkFields()
    }

    fun onPasswordChange(newPassword: String){
        _password.value = newPassword
        passwordComparison()
        checkFields()
    }

    fun onConfirmPasswordChange(newConfirmPassword: String){
        _confirmPassword.value = newConfirmPassword
        passwordComparison()
        checkFields()
    }

    // TODO: Спросить можно ли здесь использовать context
    fun showDatePickerDialog(context: Context) {
        val year: Int
        val month: Int
        val day: Int

        val calendar = Calendar.getInstance()

        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.time = Date()

        val mDatePickerDialog = DatePickerDialog(
            context,
            R.style.MyDatePickerDialogTheme,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                _dateOfBirth.value = "$mDayOfMonth.${mMonth + 1}.$mYear"
            },
            year,
            month,
            day
        )

        mDatePickerDialog.show()
        checkFields()
    }

    fun buttonGenderIsPressed(who: Int) {
        when(who) {
            1 -> {
                _manIsPressed.value = !_manIsPressed.value!!
                if (_womanIsPressed.value == true) {
                    _womanIsPressed.value = !_womanIsPressed.value!!
                }
            }

            2 -> {
                _womanIsPressed.value = !_womanIsPressed.value!!
                if (_manIsPressed.value == true) {
                    _manIsPressed.value = !_manIsPressed.value!!
                }
            }
        }
        checkFields()
    }

    fun register() {
        val repositoryAuth = AuthRepository()
        val repositoryFavoriteMovies = FavoriteMoviesRepository()
        viewModelScope.launch {
            repositoryAuth.register(
                RegisterRequestBody(
                    userName = _login.value.toString(),
                    name = _name.value.toString(),
                    password = _password.value.toString(),
                    email = _email.value.toString(),
                    birthDate = "2022-11-02T09:44:56.570Z",
                    gender = 0
                )
            ).collect {}

            repositoryAuth.login(
                LoginRequestBody(
                    username = _login.value.toString(),
                    password = _password.value.toString()
                )
            ).collect{}

            repositoryFavoriteMovies.getFavoriteMovies().collect {}
        }
    }
}