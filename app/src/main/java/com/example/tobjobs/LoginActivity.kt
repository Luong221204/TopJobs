package com.example.tobjobs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tobjobs.ComposeLayout.CircularLoginProcess
import com.example.tobjobs.ComposeLayout.Email
import com.example.tobjobs.ComposeLayout.Line
import com.example.tobjobs.ComposeLayout.Logo
import com.example.tobjobs.ComposeLayout.Password
import com.example.tobjobs.Repo.LoginState
import com.example.tobjobs.Repo.RegisterState
import com.example.tobjobs.Viewmodel.LoginViewModel
import com.example.tobjobs.Viewmodel.isValidEmail
import com.example.tobjobs.Viewmodel.isValidPassword
import com.example.tobjobs.ui.theme.Green40
import com.example.tobjobs.ui.theme.rounded
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    val viewmodel : LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = this.window.decorView
        val imeVisible = MutableStateFlow<Boolean>(false)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val insetsController = WindowInsetsControllerCompat(window, window.decorView)

        insetsController.isAppearanceLightStatusBars = true
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
             imeVisible.value = insets.isVisible(WindowInsetsCompat.Type.ime())
            insets
        }

        setContent {
            LoginScreen(viewmodel,::onMainActivity) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.height(54.dp))
                    Logo(imeVisible,"Đăng nhập"){
                        LoginForm(modifier =it ,viewmodel){
                            val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }

        }
    }
    private fun onMainActivity(){
        val intent = Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
    }
}
@Composable
fun LoginScreen(viewModel: LoginViewModel,onMainActivity: ()->Unit,content :@Composable ()->Unit){
    Box(modifier = Modifier.fillMaxSize()){
        content()
        val state = viewModel.loginState.collectAsState(LoginState.Idle)
        when(state.value){
            is LoginState.Idle -> {
            }
            is LoginState.Loading -> {
                CircularLoginProcess(true)
            }
            is LoginState.Success ->{
                onMainActivity()
            }
            is LoginState.Error->{
                Toast.makeText(LocalContext.current, (state.value as LoginState.Error).error ,Toast.LENGTH_SHORT).show()
            }
        }

    }

}

@Composable
fun LoginForm(modifier: Modifier,viewModel: LoginViewModel,onRequestedToRegisterActivity:()->Unit) {
    val keyBoardController  = LocalSoftwareKeyboardController.current
    val isLoginEnable by derivedStateOf {
        viewModel.password.isValidPassword() && viewModel.email.isValidEmail()
    }
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
    ) {
        val (emailRef,passwordRef,forgot,login,line) = createRefs()
        Email(
            text = viewModel.email,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(emailRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            isWarning = viewModel.isEmailWarning,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            onFocus = {
                viewModel.isValidEmail(it)
            }
        ) {
            viewModel.onEmailChange(it)
        }
        Password(
            text = viewModel.password,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(passwordRef) {
                    top.linkTo(emailRef.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            isWarning = viewModel.isPasswordWarning,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyBoardController?.hide()
                }
            ),
            onFocus = {

                viewModel.isValidPassword(it)
            }
        ) {
            viewModel.onPasswordChange(it)
        }
        Text(
            text = "Quên mật khẩu",
            style = TextStyle(fontSize = 15.sp, color =  colorResource(R.color.green)),
            modifier = Modifier.constrainAs(forgot){
                top.linkTo(passwordRef.bottom,16.dp)
                end.linkTo(passwordRef.end)
            }
        )
        Button(
            onClick = {viewModel.doLogIn()},
            enabled = isLoginEnable,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(login) {
                    top.linkTo(forgot.bottom, 16.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .height(56.dp),
            shape = RoundedCornerShape(rounded),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green40,
                disabledContainerColor = Color.Gray,
            ),
        ) {
            Text(
                text = "Đăng nhập",
                color = Color.White
            )
        }
        Line("Hoặc đăng nhập bằng", modifier = Modifier
            .constrainAs(line) {
                top.linkTo(login.bottom, 24.dp)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }
            .fillMaxWidth())

        val (other,regis) = createRefs()
        Row(
            modifier= Modifier
                .constrainAs(other) {
                    top.linkTo(line.bottom, 24.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(R.drawable.facebook),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(R.drawable.google),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }

        Text( text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    color = Color.Black.copy(alpha = 0.2f),
                )
            ){
                append("Chưa có tài khoản?  ")
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    color = Green40,
                    fontWeight = FontWeight.Bold

                )
            ){
                append("Đăng ký ngay")
            }

        },
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(regis) {
                    top.linkTo(other.bottom, 32.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .clickable {
                    onRequestedToRegisterActivity()
                }
        )
    }
}



