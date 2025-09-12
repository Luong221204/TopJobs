package com.example.tobjobs.Viewmodel

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tobjobs.Domain.Error
import com.example.tobjobs.NetWorkService.RegisterDto
import com.example.tobjobs.Repo.LoginState
import com.example.tobjobs.Repo.RegisterRepository
import com.example.tobjobs.Repo.RegisterState
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
class RegisterViewmodel
@Inject constructor(
    private val repository: RegisterRepository
):ViewModel(){

    var name by   mutableStateOf("")
        private set
    var email by   mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var passwordMatch by mutableStateOf("")
        private set
    var isEmailWarning by  mutableStateOf(false)
        private set
    var isPasswordWarning by mutableStateOf(false)
        private set
    var isNameWarning by  mutableStateOf(false)
        private set
    var isPasswordMatchWarning by mutableStateOf(false)
        private set

    var agreeWithPolicy by mutableStateOf(false)
    private val _registerState = MutableSharedFlow<RegisterState>()
    val registerState : SharedFlow<RegisterState> = _registerState

    fun register(){
        val user = RegisterDto(name,email,password,passwordMatch,agreeWithPolicy)
        viewModelScope.launch {
            _registerState.emit(RegisterState.Loading)
            try {
                val response = repository.register(user)
                if(response.user != null) {
                    //xử lí user đăng kí thành công
                    _registerState.emit(RegisterState.Success(response.user))
                }
            }catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val gson = Gson()
                if (response != null) {
                    val error=gson.fromJson(response, Error::class.java)
                    _registerState.emit(RegisterState.Error(error.message[0]))
                }
            } catch (e: Exception) {
                _registerState.emit(RegisterState.Error("Lỗi hệ thống"))
            }
        }
    }

    private fun isValidFormat(password: String):Boolean {
        val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[!@#\$%^&*(),.?\":{}|<>]).{8,}$")
        return password.matches(passwordRegex)
    }

    fun isValidPassword(isFocus: Boolean){
        isPasswordWarning=!isFocus && !isValidFormat(password)
    }

    fun isValidEmail(isFocus:Boolean) {
        isEmailWarning=!isFocus && !(email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    fun isValidPasswordMatch(isFocus:Boolean) {
        isPasswordMatchWarning=!isFocus && !isValidFormat(passwordMatch)

    }

    //dành cho ô text email
    fun onEmailChange(newValue: String) {
        email = newValue
    }

    //dành cho ô text pass
    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun onPasswordMatchChange(newValue: String) {
        passwordMatch = newValue
    }

    fun onNameChange(newValue: String) {
        name = newValue
    }

    fun isValidName(isFocus: Boolean) {
        isNameWarning = !isFocus && name.isEmpty()
    }
    fun onChangeOpinion(){
        agreeWithPolicy = !agreeWithPolicy
    }
}

fun String.isValidEmail():Boolean{
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword():Boolean{
    val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[!@#\$%^&*(),.?\":{}|<>]).{8,}$")
    return this.matches(passwordRegex)
}