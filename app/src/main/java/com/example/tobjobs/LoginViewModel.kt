package com.example.tobjobs

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepo: LoginRepository
):ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState : StateFlow<LoginState> = _loginState
    fun doLogIn(loginDto: LoginDto){
        viewModelScope.launch {
            _loginState.emit(LoginState.Loading)
            try{
                val response=loginRepo.loginWithEmail(loginDto)
                if(response.user != null){
                    _loginState.emit(LoginState.Success(response.user))
                }else {
                    _loginState.emit(LoginState.Error(response.message))
                }

            }catch (e:Exception){
                _loginState.emit(LoginState.Error("Lỗi hệ thống"))
            }
        }
    }
}