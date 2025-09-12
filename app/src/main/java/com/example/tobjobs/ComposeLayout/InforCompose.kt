package com.example.tobjobs.ComposeLayout

import android.util.Log
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.layoutId
import androidx.lifecycle.ViewModel
import com.example.tobjobs.LoginForm
import com.example.tobjobs.R
import com.example.tobjobs.Viewmodel.LoginViewModel
import com.example.tobjobs.Viewmodel.RegisterViewmodel
import com.example.tobjobs.ui.theme.Changa_one
import com.example.tobjobs.ui.theme.Green40
import com.example.tobjobs.ui.theme.Green405
import com.example.tobjobs.ui.theme.Green408
import com.example.tobjobs.ui.theme.TobJobsTheme
import com.example.tobjobs.ui.theme.TopJobsTheme
import com.example.tobjobs.ui.theme.rounded
import kotlinx.coroutines.flow.StateFlow
val height = 20
val space = 8

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Email(
    text:String,
    modifier: Modifier,
    isWarning: Boolean=false,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onFocus:(Boolean)->Unit,
    onValueTextChange:(String)->Unit
){

    var isFirstPressed by remember { mutableStateOf(false) }
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            shape = RoundedCornerShape(rounded),
            value = text,
            onValueChange = { onValueTextChange(it) },
            label = { Text("Email") },
            placeholder = { Text("example@email.com", color = TopJobsTheme.color.BlackFade) },
            singleLine = true,
            leadingIcon = {
                if(isWarning){
                    Image(
                        painter = painterResource(R.drawable.warning),
                        contentDescription = null,
                        modifier = Modifier.size(TopJobsTheme.iconSize.outLineTextIconSize),
                        colorFilter = ColorFilter.tint(color = Color.Red)
                    )
                }else{
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon",
                        tint = Green40
                    )
                }

            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Green408,
                unfocusedBorderColor = Green405,
                errorBorderColor = Color.Red
            ),
            isError = isWarning,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = Modifier.fillMaxWidth().onFocusEvent {
                if(isFirstPressed){
                    onFocus(it.isFocused)
                }else {
                    if(it.isFocused) isFirstPressed=true
                }
            }
        )
        Spacer( modifier= Modifier.height(if(isWarning) TopJobsTheme.fourFoldDimension.v2 else  TopJobsTheme.fourFoldDimension.v0))
        Text("Email không hợp lệ",modifier= Modifier.height(if(isWarning) height.dp else TopJobsTheme.fourFoldDimension.v0) )
    }


}

@Composable
fun Password(
    text:String,
    modifier: Modifier,
    isWarning:Boolean=false,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onFocus:(Boolean)->Unit,
    onValueTextChange:(String)->Unit
){
    var isPasswordVisible  by remember { mutableStateOf(false) }
    var isFirstPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            shape = RoundedCornerShape(rounded),
            value = text,
            onValueChange = { onValueTextChange(it) },
            label = { Text("Mật khẩu") },
            placeholder = { Text("Nhập mật khẩu của bạn", color = TopJobsTheme.color.BlackFade) },
            singleLine = true,
            leadingIcon = {
                if(isWarning){
                    Image(
                        painter = painterResource(R.drawable.warning),
                        contentDescription = null,
                        modifier = Modifier.size(TopJobsTheme.iconSize.outLineTextIconSize),
                        colorFilter = ColorFilter.tint(color = TopJobsTheme.color.Error)
                    )
                }else{
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon",
                        tint = Green40
                    )
                }

            },
            trailingIcon = {
                Image(
                    painter = if(isPasswordVisible) painterResource(R.drawable.hide) else painterResource(
                        R.drawable.show
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(TopJobsTheme.iconSize.outLineTextIconTrailingSize).clickable { isPasswordVisible = !isPasswordVisible }
                )

            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Green408,
                unfocusedBorderColor = Green405,
                errorBorderColor = TopJobsTheme.color.Error
            ),
            isError = isWarning,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = Modifier.fillMaxWidth().onFocusEvent {
                if(isFirstPressed){
                    onFocus(it.isFocused)
                }else {
                    if(it.isFocused) isFirstPressed=true
                }
            },
            interactionSource = interactionSource
        )
        Spacer( modifier= Modifier.height(if(isWarning) space.dp else 0.dp))
        Text("Mật khẩu phải có ít nhất 8 ký tự",modifier= Modifier.height(if(isWarning) height.dp else 0.dp) )
    }


}

@Composable
fun Name(
    text:String,
    modifier: Modifier,
    isWarning: Boolean=false,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onFocus:(Boolean)->Unit,
    onValueTextChange:(String)->Unit
){
    var isFirstPressed by remember { mutableStateOf(false) }
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            shape = RoundedCornerShape(rounded),
            value = text,
            onValueChange = { onValueTextChange(it) },
            label = { Text("Nhập họ và tên đầy đủ") },
            placeholder = { Text("Nhập họ và tên đầy đủ", color = TopJobsTheme.color.BlackFade) },
            singleLine = true,
            leadingIcon = {
                if(isWarning){
                    Image(
                        painter = painterResource(R.drawable.warning),
                        contentDescription = null,
                        modifier = Modifier.size(TopJobsTheme.iconSize.outLineTextIconSize),
                        colorFilter = ColorFilter.tint(color = TopJobsTheme.color.Error)
                    )
                }else{
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Green40
                    )
                }

            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor =Green408,
                unfocusedBorderColor = Green405,
                errorBorderColor = TopJobsTheme.color.Error
            ),
            isError = isWarning,
            keyboardOptions = keyboardOptions ,
            keyboardActions = keyboardActions,
            modifier = Modifier.fillMaxWidth().onFocusEvent {
                if(isFirstPressed){
                    onFocus(it.isFocused)
                }else {
                    if(it.isFocused) isFirstPressed=true
                }
            }
        )
        Spacer( modifier= Modifier.height(if(isWarning) space.dp else 0.dp))
        Text("Chúng tôi cần bạn cung cấp tên ",modifier= Modifier.height(if(isWarning) height.dp else 0.dp) )
    }

}
@Composable
fun PasswordAgain(
    text:String,
    modifier: Modifier,
    isWarning:Boolean=false,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onFocus:(Boolean)->Unit,
    onValueTextChange:(String)->Unit
){
    var isPasswordVisible  by remember { mutableStateOf(false) }
    var isFirstPressed by remember { mutableStateOf(false) }
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            shape = RoundedCornerShape(rounded),
            value = text,
            onValueChange = { onValueTextChange(it) },
            label = { Text("Mật khẩu") },
            placeholder = { Text("Nhập lại mật khẩu của bạn", color = TopJobsTheme.color.BlackFade) },
            singleLine = true,
            leadingIcon = {
                if(isWarning){
                    Image(
                        painter = painterResource(R.drawable.warning),
                        contentDescription = null,
                        modifier = Modifier.size(TopJobsTheme.iconSize.outLineTextIconSize),
                        colorFilter = ColorFilter.tint(color = TopJobsTheme.color.Error)
                    )
                }else{
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon",
                        tint = Green40
                    )
                }

            },
            trailingIcon = {
                Image(
                    painter = if(isPasswordVisible) painterResource(R.drawable.hide) else painterResource(
                        R.drawable.show
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(TopJobsTheme.iconSize.outLineTextIconTrailingSize).clickable { isPasswordVisible = !isPasswordVisible
                    }
                )

            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Green408,
                unfocusedBorderColor = Green405,
                errorBorderColor =TopJobsTheme.color.Error
            ),
            isError = isWarning,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = Modifier.fillMaxWidth().onFocusEvent {
                if(isFirstPressed){
                    onFocus(it.isFocused)
                }else {
                    if(it.isFocused) isFirstPressed=true
                }
            }
        )
        Spacer( modifier= Modifier.height(if(isWarning) space.dp else 0.dp))
        Text("Mật khẩu phải có ít nhất 8 ký tự ",modifier= Modifier.height(if(isWarning) height.dp else 0.dp) )
    }

}


@Composable
fun Line(
    text: String,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (left, title, right) = createRefs()

        Text(
            text = text,
            color = Color.Black.copy(alpha = 0.2f),
            modifier = Modifier.constrainAs(title) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
            }
        )

        Box(
            modifier = Modifier
                .constrainAs(left) {
                    top.linkTo(title.top)
                    bottom.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(title.start, 8.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(1.dp)
                }
                .background(Color.Gray.copy(alpha = 0.2f))
        )

        // Line bên phải
        Box(
            modifier = Modifier
                .constrainAs(right) {
                    top.linkTo(title.top)
                    bottom.linkTo(title.bottom)
                    start.linkTo(title.end, 8.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(1.dp)
                }
                .background(Color.Gray.copy(alpha = 0.2f))
        )
    }
}
@OptIn(ExperimentalMotionApi::class)
@Composable
fun Logo(
    ime: StateFlow<Boolean>,
    title:String,
    content:@Composable (Modifier)->Unit
){
    var isFirst by  remember { mutableStateOf(true) }
    var progress by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        ime.collect{
            if(isFirst){
                progress = 0f
                isFirst=false
            }else{
                animate(
                    initialValue = if(it) 0f else 1f,
                    targetValue = if(it) 1f else 0f,
                    animationSpec = tween(1000)
                ) { value, _ ->
                    progress = value
                }
            }
        }
    }

    MotionLayout(
        motionScene = motionScene1,
        progress = progress,
        modifier = Modifier.fillMaxSize().background(color = Color.White)
    ){
        val props_app = customProperties("app_name")
        val fontSize_app = props_app.float("fontSize") ?: 16f
        val props_title = customProperties("title")
        val fontSize_title = props_title.float("fontSize") ?: 16f
        val props_slogan = customProperties("slogan")
        val fontSize_slogan = props_slogan.float("fontSize") ?: 16f
        Text( text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = fontSize_app.sp,
                    color = colorResource(R.color.green_aggressive),
                    fontFamily = Changa_one,
                    fontWeight = FontWeight.Bold
                )
            ){
                append("top")
            }
            withStyle(
                style = SpanStyle(
                    fontSize = fontSize_app.sp,
                    fontFamily = Open_sans,
                    color = colorResource(R.color.green),
                    fontWeight = FontWeight.ExtraBold
                )
            ){
                append("Jobs")
            }

        },
            modifier = Modifier.layoutId("app_name"),
            textAlign = TextAlign.Center
        )
        Text(text = "Chào mừng bạn đén với TopJobs", fontSize = fontSize_slogan.sp,
            fontFamily = Open_sans,
            textAlign = TextAlign.Center,

            modifier = Modifier.layoutId("slogan"))
        Text(text = title, fontSize = fontSize_title.sp,
            fontFamily = Open_sans,
            textAlign = TextAlign.Center,

            modifier = Modifier.layoutId("title").clickable {})
        content(Modifier.layoutId("nested"))
    }

}
