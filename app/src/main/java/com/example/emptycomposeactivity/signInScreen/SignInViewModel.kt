package com.example.emptycomposeactivity.signInScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emptycomposeactivity.network.auth.AuthRepository
import com.example.emptycomposeactivity.network.auth.LoginRequestBody
import com.example.emptycomposeactivity.network.favoriteMovies.FavoriteMoviesRepository
import com.example.emptycomposeactivity.network.movies.MoviesRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {

    private val _login = MutableLiveData("")
    var login: LiveData<String> = _login

    private val _password = MutableLiveData("")
    var password: LiveData<String> = _password

    private val _allFieldsFilled = MutableLiveData(false)
    var allFieldsFilled: LiveData<Boolean> = _allFieldsFilled

    private fun checkFields() {
        val login = _login.value
        val password = _password.value
        if (login != null && password != null) {
            _allFieldsFilled.value = login.isNotEmpty() && password.isNotEmpty()
        }
    }

    fun onLoginChange(newLogin: String) {
        _login.value = newLogin
        checkFields()
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        checkFields()
    }

    fun comeIn(){
        val repositoryAuth = AuthRepository()
        val repositoryFavoriteMovies = FavoriteMoviesRepository()
        val repositoryMovies = MoviesRepository()
        viewModelScope.launch {
            repositoryAuth.login(
                LoginRequestBody(
                    username = _login.value.toString(),
                    password = _password.value.toString()
                )
            ).collect{}

            repositoryFavoriteMovies.getFavoriteMovies().collect {}
            repositoryMovies.getMovies().collect{}
        }
    }
}