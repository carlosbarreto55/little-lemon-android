package com.example.littlelemon.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object LittleLemonColor {
    val green = Color(0xFF495E57)
    val yellow = Color(0xFFF4CE14)
    val salmon = Color(0xFFEE9972)
    val peach = Color(0xFFFBDABB)
    val cloud = Color(0xFFEDEFEE)
    val charcoal = Color(0xFF333333)
}

private val colorScheme = lightColorScheme(
    primary = LittleLemonColor.green,
    onPrimary = Color.White,
    secondary = LittleLemonColor.yellow,
    onSecondary = LittleLemonColor.charcoal,
    background = Color.White,
    onBackground = LittleLemonColor.charcoal,
    surface = Color.White,
    onSurface = LittleLemonColor.charcoal,
)

@Composable
fun LittleLemonTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = colorScheme, content = content)
}
