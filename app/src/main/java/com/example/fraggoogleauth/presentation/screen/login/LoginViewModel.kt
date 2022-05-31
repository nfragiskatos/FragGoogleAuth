package com.example.fraggoogleauth.presentation.screen.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fraggoogleauth.domain.model.MessageBarState
import com.example.fraggoogleauth.domain.repository.Repository
import com.example.fraggoogleauth.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _signedInState: MutableState<Boolean> = mutableStateOf(false)
    val signedInState: State<Boolean> = _signedInState

    private val _messageBarState: MutableState<MessageBarState> = mutableStateOf(MessageBarState())
    val messageBarState: State<MessageBarState> = _messageBarState

    private val _isUserAuthenticated: MutableState<Boolean> =
        mutableStateOf(false)
    val isUserAuthenticated: State<Boolean> = _isUserAuthenticated

    init {
        viewModelScope.launch {
            repository.readSignedInState().collect { completed ->
                _signedInState.value = completed
            }
        }
    }

    fun saveSignedInState(signedInState: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveSignedInState(signedInState)
        }
    }

    fun updateMessageBarState() {
        _messageBarState.value = MessageBarState(error = GoogleAccountNotFoundException())
    }

    fun verifyTokenOnBackend(tokenId: String) {
        _isUserAuthenticated.value = false
        try {
            viewModelScope.launch(Dispatchers.IO) {
                when (val response = repository.verifyTokenOnBackend(tokenId)) {
                    is RequestState.Error -> {
                        _messageBarState.value = MessageBarState(error = response.error)
                    }
                    is RequestState.Success -> {
                        _isUserAuthenticated.value = response.data
                        _messageBarState.value = MessageBarState(
                            message = response.message,
                        )
                    }
                    else -> {}
                }

            }
        } catch (e: Exception) {
            _messageBarState.value = MessageBarState(error = e)
        }
    }
}

class GoogleAccountNotFoundException(override val message: String? = "Google Account Not Found.") :
    Exception()