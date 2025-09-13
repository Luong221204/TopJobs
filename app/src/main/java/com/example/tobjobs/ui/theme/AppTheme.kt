package com.example.tobjobs.ui.theme

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.PrimaryKey
import com.example.tobjobs.R


data class Alpha(
    val a1: Float = 0.1f,
    val a2: Float = 0.2f,
    val a3: Float = 0.3f,
    val a4: Float = 0.4f,
    val a5: Float = 0.5f,
    val a6: Float = 0.6f,
    val a7: Float = 0.7f,
    val a8: Float = 0.8f,
    val a9: Float = 0.9f,
    val a10: Float = 1.0f,
)

data class FiftyFold(
    val d1: Dp = 50.dp,
    val d2: Dp = 100.dp,
    val d3: Dp = 150.dp,
    val d4: Dp = 200.dp
)

data class FourFoldDimension(
    var v0 :Dp = 0.dp,
    var v1: Dp = 4.dp,
    var v2: Dp = 8.dp,
    var v3: Dp = 12.dp,
    var v4: Dp = 16.dp,
    var v5: Dp = 20.dp,
    val v6: Dp = 24.dp,
    val v7: Dp = 28.dp,
    val v8: Dp = 32.dp,
    val v9: Dp = 36.dp,
    val v10: Dp = 40.dp,
    val v11: Dp = 44.dp,
    val v12: Dp = 48.dp,
    val v16: Dp = 64.dp,
    val v14: Dp = 56.dp,
    val v18: Dp = 72.dp,
    val v44: Dp = 176.dp
)


data class IconSizeDimension(
    val outLineTextIconSize :Dp = 24.dp,
    val outLineTextIconTrailingSize :Dp = 20.dp
)

data class Color(
    val BlackFade: Color = Color.Black.copy(0.2f),
    val Error:Color = Errors
)
data class ThinDimension(
    val t1: Dp = 0.5.dp,
    val t2: Dp = 1.dp,
    val t3: Dp = 2.dp,
    val t4: Dp = 3.dp,
    )

data class BlockDimension(
    val b1: Dp = 10.dp,
    val b2: Dp = 20.dp,
    val b3: Dp = 30.dp,
    val b4: Dp = 40.dp,
    val b5: Dp = 50.dp,
    val b6: Dp = 60.dp,
    val b7: Dp = 70.dp,
    val b8: Dp = 80.dp,
    val b9: Dp = 90.dp,
)

data class RoundedCornerDimension(
    val r1: Dp = 1.dp,
    val r2: Dp = 2.dp,
    val r3: Dp = 3.dp,
    val r4: Dp = 4.dp,
    val r5: Dp = 5.dp,
    val r6: Dp = 6.dp,
    val r7: Dp = 7.dp,
    val r8: Dp = 8.dp,
    val r9: Dp = 9.dp,
    val r10: Dp = 10.dp,
    val r11: Dp = 11.dp,
    val r12: Dp = 12.dp,
    val r13: Dp = 13.dp,
    val r14: Dp = 14.dp,
    val r15: Dp = 15.dp,
    val r16: Dp = 16.dp,
    val r17: Dp = 17.dp,
    val r18: Dp = 18.dp,
    val r19: Dp = 19.dp,
    val r20: Dp = 20.dp,
    val r30: Dp = 30.dp,
)

data class AppTypoTheme(
    val logoStyle :TextStyle = TextStyle(
    ),
    val primary:TextStyle = TextStyle(
        fontSize = 15.sp,
        color =  Green40,
        fontFamily = Roboto
        ),
    val buttonStyle:TextStyle = TextStyle(
        color = Color.White,
        fontFamily = Roboto,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    ),
    val labelStyle:TextStyle  = TextStyle(
        fontSize = 14.sp,
        fontFamily = Roboto,
        color = Teal200
    ),
    val placeholderStyle:TextStyle  = TextStyle(
        fontSize = 14.sp,
        fontFamily = Roboto,
        color = Color.Black.copy(0.2f)
    ),
    val inValidStyle:TextStyle  = TextStyle(
        fontSize = 13.sp,
        fontFamily = Roboto,
        color = Errors
    ),
    val spanStylePrimary: SpanStyle = SpanStyle(
        fontSize = 14.sp,
        color = Color.Black,
        fontFamily = Roboto
    ),
    val spanStyleSecondary: SpanStyle = SpanStyle(
        fontSize = 14.sp,
        color = Green40,
        fontFamily = Roboto
    ),

    val spanStylePrimaryFade: SpanStyle = SpanStyle(
        fontFamily = Roboto,
        fontSize = 14.sp,
        color = Color.Black.copy(0.2f),
    ),
    val spanStyleSecondaryExtra: SpanStyle = SpanStyle(
        fontSize = 14.sp,
        color = Green40,
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold
    ),

)
val LocalAlpha = staticCompositionLocalOf { Alpha() }
val LocalIconSize = staticCompositionLocalOf { IconSizeDimension() }
val LocalAppTypo = staticCompositionLocalOf { AppTypoTheme() }
val LocalRoundedDp = staticCompositionLocalOf { RoundedCornerDimension() }
val LocalBlockDp = staticCompositionLocalOf { BlockDimension() }
val LocalThinDp = staticCompositionLocalOf { ThinDimension() }
val LocalFourFoldDp = staticCompositionLocalOf { FourFoldDimension() }
val LocalFiftyFoldDp = staticCompositionLocalOf { FiftyFold() }
val LocalColor = staticCompositionLocalOf { Color() }

@Composable
fun TopJobsTheme(content: @Composable ()->Unit){
    content()
}

object TopJobsTheme {
    val appTypoTheme: AppTypoTheme
        @Composable
        get() = LocalAppTypo.current
      val blockDimension: BlockDimension
        @Composable
        get() = LocalBlockDp.current
    val roundedCornerDimension: RoundedCornerDimension
        @Composable
        get() = LocalRoundedDp.current
    val alpha: Alpha
        @Composable
        get() = LocalAlpha.current
    val thinDimension: ThinDimension
        @Composable
        get() = LocalThinDp.current
    val fourFoldDimension: FourFoldDimension
        @Composable
        get() = LocalFourFoldDp.current
    val fiftyFold: FiftyFold
        @Composable
        get() = LocalFiftyFoldDp.current
    val iconSize: IconSizeDimension
        @Composable
        get() = LocalIconSize.current
    val color: com.example.tobjobs.ui.theme.Color
        @Composable
        get() = LocalColor.current
}