package com.example.fraggoogleauth.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val LoadingBlue = Color(0xFF1A73E8)
val ErrorRed = Color(0xFFFF6c60)
val InfoGreen = Color(0xFF00C096)
val Gray200 = Color(0xFF819CA9)
val Gray500 = Color(0xFF546E7A)
val Gray700 = Color(0xFF29434E)
val Colors.topAppBarContentColor: Color
    get() = if (isLight) Color.White else Color.LightGray

val Colors.topAppBarBackgroundColor: Color
    get() = if (isLight) Gray500 else Color.Black