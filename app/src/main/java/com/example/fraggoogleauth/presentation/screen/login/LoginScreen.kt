package com.example.fraggoogleauth.presentation.screen.login

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val signedInState by loginViewModel.signedInState
    val messageBarState by loginViewModel.messageBarState

    Scaffold(
        topBar = {
            LoginTopBar()
        },
        content = {
            LoginContent(
                signedInState = signedInState,
                messageBarState = messageBarState,
                onButtonClicked = {
                    loginViewModel.saveSignedInState(true)
                }
            )
        }
    )
}