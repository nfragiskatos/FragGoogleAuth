package com.example.fraggoogleauth.presentation.screen.login

import android.app.Activity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.fraggoogleauth.navigation.Screen
import com.example.fraggoogleauth.presentation.screen.common.StartActivityForResult
import com.example.fraggoogleauth.presentation.screen.common.signIn

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val signedInState by loginViewModel.signedInState
    val messageBarState by loginViewModel.messageBarState
    val isUserAuthenticated by loginViewModel.isUserAuthenticated

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

    val activity = LocalContext.current as Activity
    StartActivityForResult(
        key = signedInState,
        onResultReceived = { tokenId ->
            loginViewModel.verifyTokenOnBackend(tokenId)
        },
        onDialogDismissed = {
            loginViewModel.saveSignedInState(false)
        }
    ) { activityLauncher ->
        if (signedInState) {
            signIn(
                activity = activity,
                launchActivityResult = { intentSenderRequest ->
                    activityLauncher.launch(intentSenderRequest)
                },
                accountNotFound = {
                    loginViewModel.saveSignedInState(false)
                    loginViewModel.updateMessageBarState()
                }
            )
        }
    }

    LaunchedEffect(key1 = isUserAuthenticated) {
        if (isUserAuthenticated) {
            navigateToProfileScreen(navController)
        } else {
            loginViewModel.saveSignedInState(false)
        }
    }
}

private fun navigateToProfileScreen(navController: NavHostController) {
    navController.navigate(Screen.Profile.route) {
        popUpTo(route = Screen.Login.route) {
            inclusive = true
        }
    }
}