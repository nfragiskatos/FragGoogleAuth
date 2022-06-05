package com.example.fraggoogleauth.presentation.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.fraggoogleauth.R
import com.example.fraggoogleauth.component.GoogleButton
import com.example.fraggoogleauth.component.MessageBar
import com.example.fraggoogleauth.domain.model.MessageBarState
import com.example.fraggoogleauth.ui.theme.LoadingBlue

@Composable
fun ProfileContent(
    isLoading: Boolean,
    messageBarState: MessageBarState,
    firstName: String,
    onFirstNameChanged: (String) -> Unit,
    lastName: String,
    onLastNameChanged: (String) -> Unit,
    emailAddress: String?,
    profilePhoto: String?,
    onSignOutClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.weight(1f)) {
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = LoadingBlue
                )
            } else {
                MessageBar(messageBarState = messageBarState)
            }
        }
        Column(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth(0.7f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CentralContent(
                firstName = firstName,
                onFirstNameChanged = onFirstNameChanged,
                lastName = lastName,
                onLastNameChanged = onLastNameChanged,
                emailAddress = emailAddress ?: "",
                profilePhoto = profilePhoto ?: "",
                onSignOutClicked = onSignOutClicked
            )
        }
    }
}

@Composable
private fun CentralContent(
    firstName: String,
    onFirstNameChanged: (String) -> Unit,
    lastName: String,
    onLastNameChanged: (String) -> Unit,
    emailAddress: String,
    profilePhoto: String,
    onSignOutClicked: () -> Unit
) {
    val painter = rememberImagePainter(data = profilePhoto) {
        crossfade(1000)
        placeholder(R.drawable.ic_placeholder)
    }

    Image(
        modifier = Modifier
            .padding(bottom = 40.dp)
            .size(150.dp)
            .clip(CircleShape),
        painter = painter,
        contentDescription = "Profile Photo"
    )

    OutlinedTextField(
        value = firstName,
        onValueChange = {
            onFirstNameChanged(it)
        },
        label = { Text(text = "First Name") },
        textStyle = MaterialTheme.typography.body1,
        singleLine = true
    )
    OutlinedTextField(
        value = lastName,
        onValueChange = {
            onLastNameChanged(it)
        },
        label = { Text(text = "Last Name") },
        textStyle = MaterialTheme.typography.body1,
        singleLine = true
    )
    OutlinedTextField(
        value = emailAddress.toString(),
        onValueChange = { },
        label = { Text(text = "Email Address") },
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        enabled = false
    )
    GoogleButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        primaryText = "Sign Out",
        secondaryText = "Sign Out",
        onClick = onSignOutClicked
    )

}