package com.example.fraggoogleauth.presentation.screen.profile

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
import com.google.android.gms.auth.api.identity.Identity

@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val firstName by profileViewModel.firstName
    val lastName by profileViewModel.lastName
    val user by profileViewModel.user
    val messageBarState by profileViewModel.messageBarState
    val isLoading by profileViewModel.isLoading
    val sessionCleared by profileViewModel.sessionCleared
    val isUserAuthenticated by profileViewModel.isUserAuthenticated

    Scaffold(
        topBar = {
            ProfileTopBar(
                onSave = {
                    profileViewModel.updateUserInfo()
                },
                onDeleteAllConfirmed = {
                    profileViewModel.deleteUser()
                }
            )
        },
        content = {
            ProfileContent(
                isLoading = isLoading,
                messageBarState = messageBarState,
                firstName = firstName,
                onFirstNameChanged = {
                    profileViewModel.updateFirstName(it)
                },
                lastName = lastName,
                onLastNameChanged = {
                    profileViewModel.updateLastName(it)
                },
                emailAddress = user?.emailAddress,
                profilePhoto = user?.profilePhoto,
                onSignOutClicked = {
                    profileViewModel.clearSession()
                }
            )
        }
    )

    val activity = LocalContext.current as Activity

    StartActivityForResult(
        key = isUserAuthenticated,
        onResultReceived = { tokenId ->
            profileViewModel.verifyTokenOnBackend(tokenId = tokenId)
        },
        onDialogDismissed = {
            profileViewModel.saveSignedInState(signedInState = false)
            navigateToLoginScreen(navController = navController)
        }
    ) { activityLauncher ->
        if (!isUserAuthenticated) {
            signIn(
                activity = activity,
                launchActivityResult = { intentSenderRequest ->
                    activityLauncher.launch(intentSenderRequest)
                },
                accountNotFound = {
                    profileViewModel.saveSignedInState(false)
                    navigateToLoginScreen(navController = navController)
                }
            )
        }
    }

    LaunchedEffect(key1 = sessionCleared) {
        if (sessionCleared) {
            val oneTapClient = Identity.getSignInClient(activity)
            oneTapClient.signOut()
            profileViewModel.saveSignedInState(signedInState = false)
            navigateToLoginScreen(navController = navController)
        }
    }
}

private fun navigateToLoginScreen(
    navController: NavHostController
) {
    navController.navigate(route = Screen.Login.route) {
        popUpTo(route = Screen.Profile.route) {
            inclusive = true
        }
    }
}