package com.example.fraggoogleauth.presentation.screen.profile

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.fraggoogleauth.R
import com.example.fraggoogleauth.component.DisplayAlertDialog
import com.example.fraggoogleauth.ui.theme.topAppBarBackgroundColor
import com.example.fraggoogleauth.ui.theme.topAppBarContentColor

@Composable
fun ProfileTopBar(
    onSave: () -> Unit,
    onDeleteAllConfirmed: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Profile",
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            ProfileTopBarActions(
                onSave = onSave,
                onDeleteAllConfirmed = onDeleteAllConfirmed
            )
        }
    )
}

@Composable
fun ProfileTopBarActions(
    onSave: () -> Unit,
    onDeleteAllConfirmed: () -> Unit
) {
    var openDialog by remember {
        mutableStateOf(false)
    }

    DisplayAlertDialog(
        openDialog = openDialog,
        onYesClicked = { onDeleteAllConfirmed() },
        onDialogClosed = { openDialog = false }
    )

    SaveAction(onSave)
    DeleteAllAction(onDeleteClicked = { openDialog = true })
}

@Composable
fun SaveAction(onSave: () -> Unit) {
    IconButton(onClick = onSave) {
        Icon(
            painter = painterResource(id = R.drawable.ic_save),
            contentDescription = "Save",
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun DeleteAllAction(onDeleteClicked: () -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = "Vertical Menu",
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onDeleteClicked()
            }) {
                Text(
                    text = "Delete Account",
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}

@Composable
@Preview
fun ProfileTopBarPreview() {
    ProfileTopBar(onSave = {}, onDeleteAllConfirmed = {})
}