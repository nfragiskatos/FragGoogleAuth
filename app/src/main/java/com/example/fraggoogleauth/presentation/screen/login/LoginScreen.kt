package com.example.fraggoogleauth.presentation.screen.login

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.fraggoogleauth.domain.model.MessageBarState

@Composable
fun LoginScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            LoginTopBar()
        },
        content = {
            // TODO replace these dummy values
            LoginContent(
                signedInState = false,
                messageBarState = MessageBarState(),
                onButtonClicked = {}
            )
        }
    )
}