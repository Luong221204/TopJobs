package com.example.tobjobs.ComposeLayout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onVisibilityChanged
import androidx.compose.ui.unit.dp
import com.example.tobjobs.ui.theme.Green40


@Composable
fun CircularLoginProcess(
    isLoading:Boolean
){
    Box(
        modifier = Modifier.alpha(if(isLoading) 1f else 0f)
            .fillMaxSize()
            .background(Color.Transparent.copy(alpha =0.5f))
            .clickable(enabled = false){ }
    ){
        CircularProgressIndicator(
            color = Green40,
            modifier = Modifier
                .align(Alignment.Center)
                .size(64.dp)
                .background(color = Color.Transparent)
        )
    }
}