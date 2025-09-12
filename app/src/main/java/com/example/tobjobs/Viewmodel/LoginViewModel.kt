package com.example.tobjobs.Viewmodel

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tobjobs.Domain.Error
import com.example.tobjobs.NetWorkService.LoginDto
import com.example.tobjobs.Repo.LoginRepository
import com.example.tobjobs.Repo.LoginState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepo: LoginRepository
):ViewModel() {
    //trang thái ô đăng nhập
    var email by   mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var isEmailWarning by  mutableStateOf(false)
        private set
    var isPasswordWarning by mutableStateOf(false)
        private set

    val isLoginEnable by mutableStateOf(!isPasswordWarning && !isEmailWarning)

    //trạng thái đăng nhập
    private val _loginState = MutableSharedFlow<LoginState>()
    val loginState : SharedFlow<LoginState> = _loginState
    fun doLogIn(){
        val loginDto = LoginDto(email,password)
        viewModelScope.launch {
            _loginState.emit(LoginState.Loading)
            try{
                val response=loginRepo.loginWithEmail(loginDto)
                if(response.user != null){
                    _loginState.emit(LoginState.Success(response.user))
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val gson = Gson()
                if (response != null) {
                    val error=gson.fromJson(response,Error::class.java)
                    _loginState.emit(LoginState.Error(error.message[0]))
                }
            } catch (e: Exception) {
                _loginState.emit(LoginState.Error("Lỗi hệ thống"))
            }
        }
    }

    //dành cho ô text email
    fun onEmailChange(newValue: String) {
        email = newValue
    }

    //dành cho ô text pass
    fun onPasswordChange(newValue: String) {
        password = newValue
    }


    fun isValidPassword(isFocus: Boolean){
        isPasswordWarning=!isFocus && password.isEmpty()
    }

    fun isValidEmail(isFocus:Boolean) {
        isEmailWarning=!isFocus && !(email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }
}