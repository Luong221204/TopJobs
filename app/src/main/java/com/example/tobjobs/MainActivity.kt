package com.example.tobjobs

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tobjobs.ui.theme.TobJobsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TobJobsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    TobJobsTheme {
        CVForm()
    }
}
@Composable
fun CVForm() {
    val viewModel :LoginViewModel = viewModel()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    val context = LocalContext.current
    var isShowLoading by remember { mutableStateOf(false) }
    val state = viewModel.loginState.collectAsState()
    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Họ tên") })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("pass") })
        Text(text = fullName)

        when(state.value){
            is LoginState.Success->{
                fullName= (state.value as LoginState.Success).user.fullName
            }
            is LoginState.Loading->{
                CircularProgressIndicator()
            }
            is LoginState.Error->{
                Toast.makeText(context, (state.value as LoginState.Error).error, Toast.LENGTH_SHORT).show()
            }

            LoginState.Idle -> {}
        }


        val coroutine = rememberCoroutineScope()
        Button(
            onClick = {
                viewModel.doLogIn(LoginDto("danit11411@gmail.com","Thai@2004"))
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Xem CV")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TobJobsTheme {
        Greeting("Android")
    }
}