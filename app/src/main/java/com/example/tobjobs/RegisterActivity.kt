package com.example.tobjobs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
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
import com.example.tobjobs.ComposeLayout.Logo
import com.example.tobjobs.ComposeLayout.Name
import com.example.tobjobs.ComposeLayout.Password
import com.example.tobjobs.ComposeLayout.PasswordAgain
import com.example.tobjobs.Repo.LoginState
import com.example.tobjobs.Repo.RegisterState
import com.example.tobjobs.Viewmodel.LoginViewModel
import com.example.tobjobs.Viewmodel.RegisterViewmodel
import com.example.tobjobs.Viewmodel.isValidEmail
import com.example.tobjobs.Viewmodel.isValidPassword
import com.example.tobjobs.ui.theme.Green40
import com.example.tobjobs.ui.theme.rounded
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {
    val viewmodel:RegisterViewmodel by viewModels()
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
            RegisterScreen(viewmodel,::onMainActivity) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.height(54.dp))
                    Logo(imeVisible,"Đăng kí"){
                        RegisterForm(modifier =it ,viewmodel)
                    }
                }
            }
        }
    }
    private fun onMainActivity(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun RegisterScreen(viewModel: RegisterViewmodel, onMainActivity: ()->Unit, content :@Composable ()->Unit){
    Box(modifier = Modifier.fillMaxSize()){
        content()
        val state = viewModel.registerState.collectAsState(RegisterState.Idle)
        when(state.value){
            is RegisterState.Idle -> {
            }
            is RegisterState.Loading -> {
                CircularLoginProcess(true)
            }
            is RegisterState.Success ->{
                Toast.makeText(
                    LocalContext.current, "ok" ,
                    Toast.LENGTH_SHORT).show()            }
            is RegisterState.Error->{
                Toast.makeText(
                    LocalContext.current, (state.value as RegisterState.Error).error ,
                    Toast.LENGTH_SHORT).show()
            }
        }

    }

}
@Composable
fun RegisterForm(modifier: Modifier,viewmodel:RegisterViewmodel) {
    val keyBoardController  = LocalSoftwareKeyboardController.current
    val isRegisterEnable by derivedStateOf {
        viewmodel.password.isValidPassword() && viewmodel.email.isValidEmail() && viewmodel.agreeWithPolicy
                &&viewmodel.name.isNotEmpty() && viewmodel.passwordMatch == viewmodel.password
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
    ) {
        val (emailRef,passwordRef,nameRef,passwordARef,regis,policy) = createRefs()
        Name(
            text = viewmodel.name,
            modifier = Modifier.fillMaxWidth().constrainAs(nameRef){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            isWarning = viewmodel.isNameWarning,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Unspecified, imeAction = ImeAction.Next),
            onFocus = {
                viewmodel.isValidName(it)
            }
        ) {
            viewmodel.onNameChange(it)
        }
        Email(
            text =  viewmodel.email,
            modifier = Modifier.fillMaxWidth().constrainAs(emailRef){
                top.linkTo(nameRef.bottom,8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            isWarning = viewmodel.isEmailWarning,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            onFocus = {
                viewmodel.isValidEmail(it)
            }
        ) {
            viewmodel.onEmailChange(it)
        }
        Password(
            text = viewmodel.password,
            modifier = Modifier.fillMaxWidth().constrainAs(passwordRef){
                top.linkTo(emailRef.bottom,8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            isWarning = viewmodel.isPasswordWarning,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
            ),
            onFocus = {
                viewmodel.isValidPassword(it)
            }
        ) {
            viewmodel.onPasswordChange(it)
        }

        PasswordAgain(
            text = viewmodel.passwordMatch,
            modifier = Modifier.fillMaxWidth().constrainAs(passwordARef){
                top.linkTo(passwordRef.bottom,8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            isWarning = viewmodel.isPasswordMatchWarning,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyBoardController?.hide()
                }
            ),
            onFocus = {
                viewmodel.isValidPasswordMatch(it)
            }
        ) {
            viewmodel.onPasswordMatchChange(it)
        }
        Row(
            modifier = Modifier.constrainAs(policy){
                top.linkTo(passwordARef.bottom,16.dp)
                end.linkTo(parent.end, )
                start.linkTo(parent.start,(-16).dp)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = viewmodel.agreeWithPolicy,
                onCheckedChange = {viewmodel.onChangeOpinion()},
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.Gray,
                    checkedColor = Green40,
                    checkmarkColor = Color.White
                )
            )
            val annotatedText = buildAnnotatedString {
                append("Tôi đã đọc và đồng ý với ")

                // Chữ "Google"
                pushStringAnnotation(tag = "Điều khoản sử dụng", annotation = "https://www.google.com")
                withStyle(style = SpanStyle(color = Green40)) {
                    append("Điều khoản sử dụng")
                }
                pop()

                append(" và ")

                // Chữ "Facebook"
                pushStringAnnotation(tag = "Chính sách bảo mật", annotation = "https://www.facebook.com")
                withStyle(style = SpanStyle(color = Green40)) {
                    append("Chính sách bảo mật")
                }
                pop()
            }

            ClickableText(
                text = annotatedText,
                onClick = { offset ->
                    annotatedText.getStringAnnotations(tag = "GOOGLE", start = offset, end = offset)
                        .firstOrNull()?.let {
                            Log.d("DUCLUONG","Click vào Google → ${it.item}")
                        }

                    annotatedText.getStringAnnotations(tag = "FACEBOOK", start = offset, end = offset)
                        .firstOrNull()?.let {
                            Log.d("DUCLUONG","Click vào Face → ${it.item}")
                        }
                },
            )


        }
        Button(
            onClick = {viewmodel.register()},
            enabled = isRegisterEnable,
            modifier = Modifier.fillMaxWidth().constrainAs(regis){
                top.linkTo(policy.bottom,20.dp)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }.height(56.dp),
            shape = RoundedCornerShape(rounded),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green40,
                disabledContainerColor = Color.Gray
            ),
        ) {
            Text(
                text = "Đăng ký",
                color = Color.White
            )
        }

        val login = createRef()


        Text( text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    color = Color.Black.copy(alpha = 0.2f),
                )
            ){
                append("Bạn đã có tài khoản?  ")
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    color = Green40,
                    fontWeight = FontWeight.Bold

                )
            ){
                append("Đăng nhập ngay")
            }

        },
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().constrainAs(login){
                top.linkTo(regis.bottom,32.dp)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }
        )
    }
}



