package com.example.fraggoogleauth.presentation.screen.profile

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

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

    Scaffold(
        topBar = {
            ProfileTopBar(
                onSave = {
                    profileViewModel.updateUserInfo()
                },
                onDeleteAllConfirmed = {}
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

                }
            )
        }
    )
}