package com.example.fraggoogleauth.presentation.screen.login

import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.fraggoogleauth.ui.theme.topAppBarBackgroundColor
import com.example.fraggoogleauth.ui.theme.topAppBarContentColor

@Composable
fun LoginTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Sign in",
                color = colors.topAppBarContentColor
            )
        },
        backgroundColor = colors.topAppBarBackgroundColor,
    )
}

@Composable
@Preview
fun LoginTopBarPreview() {
    LoginTopBar()
}