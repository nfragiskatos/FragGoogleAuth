package com.example.fraggoogleauth.presentation.screen.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fraggoogleauth.domain.model.MessageBarState
import com.example.fraggoogleauth.domain.model.User
import com.example.fraggoogleauth.domain.repository.Repository
import com.example.fraggoogleauth.util.Constants.MAX_NAME_LENGTH
import com.example.fraggoogleauth.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: State<User?> = _user

    private val _firstName: MutableState<String> = mutableStateOf("")
    val firstName: State<String> = _firstName

    private val _lastName: MutableState<String> = mutableStateOf("")
    val lastName: State<String> = _lastName

    private val _messageBarState: MutableState<MessageBarState> = mutableStateOf(MessageBarState())
    val messageBarState: State<MessageBarState> = _messageBarState

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch() {
            try {
                _isLoading.value = true
                when (val response = repository.getUserInfo()) {
                    is RequestState.Error -> {
                        _messageBarState.value = MessageBarState(error = response.error)
                    }
                    is RequestState.Success -> {
                        val user = response.data
                        _user.value = user
                        _firstName.value = user.name.split(" ").first()
                        _lastName.value = user.name.split(" ").last()
                        _messageBarState.value = MessageBarState(message = response.message)
                    }
                    else -> {}
                }
                _isLoading.value = false
            } catch (e: Exception) {
                _messageBarState.value = MessageBarState(error = e)
            }
        }
    }

    fun updateFirstName(newName: String) {
        if (newName.length < MAX_NAME_LENGTH) {
            _firstName.value = newName
        }
    }

    fun updateLastName(newName: String) {
        if (newName.length < MAX_NAME_LENGTH) {
            _lastName.value = newName
        }
    }
}