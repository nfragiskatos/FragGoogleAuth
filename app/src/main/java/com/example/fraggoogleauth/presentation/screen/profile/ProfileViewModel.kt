package com.example.fraggoogleauth.presentation.screen.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fraggoogleauth.domain.model.MessageBarState
import com.example.fraggoogleauth.domain.model.User
import com.example.fraggoogleauth.domain.model.UserUpdate
import com.example.fraggoogleauth.domain.repository.Repository
import com.example.fraggoogleauth.util.Constants.MAX_NAME_LENGTH
import com.example.fraggoogleauth.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _sessionCleared: MutableState<Boolean> = mutableStateOf(false)
    val sessionCleared: State<Boolean> = _sessionCleared

    private val _isUserAuthenticated: MutableState<Boolean> =
        mutableStateOf(true)
    val isUserAuthenticated: State<Boolean> = _isUserAuthenticated

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch() {
            try {
                _isLoading.value = true
                val response = withContext(Dispatchers.IO) {
                    repository.getUserInfo()
                }
                when (response) {
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
            } catch (e: Exception) {
                _messageBarState.value = MessageBarState(error = e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true
                if (user.value != null) {
                    when (val response = repository.getUserInfo()) {
                        is RequestState.Error -> {
                            _isLoading.value = false
                            _messageBarState.value = MessageBarState(error = response.error)
                        }
                        is RequestState.Success -> {
                            _isLoading.value = false
                            verifyAndUpdate(currentUser = response.data)
                        }
                        else -> {}
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _messageBarState.value = MessageBarState(error = e)
            }
        }
    }

    private fun verifyAndUpdate(currentUser: User) {
        val (verified, exception) =
            // check for empty fields
            if (firstName.value.isBlank() || lastName.value.isBlank()) {
                Pair(false, EmptyFieldException())
            } else {
                // check if trying to save the same values
                if (currentUser.name.split(" ").first() == firstName.value &&
                    currentUser.name.split(" ").last() == lastName.value
                ) {
                    Pair(false, NothingToUpdateException())
                } else {
                    Pair(true, null)
                }
            }

        if (verified) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.updateUser(
                        UserUpdate(
                            firstName = firstName.value,
                            lastName = lastName.value
                        )
                    )
                    when (response) {
                        is RequestState.Error -> {
                            _messageBarState.value = MessageBarState(error = response.error)
                        }
                        is RequestState.Success -> {
                            _messageBarState.value = MessageBarState(message = response.message)
                        }
                        else -> {}
                    }
                } catch (e: Exception) {
                    _messageBarState.value = MessageBarState(error = e)
                }
            }
        } else {
            _messageBarState.value = MessageBarState(error = exception)
        }
    }

    fun clearSession() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val response = repository.clearSession()
                when (response) {
                    is RequestState.Error -> {
                        _isLoading.value = false
                        _messageBarState.value = MessageBarState(error = response.error)
                    }
                    is RequestState.Success -> {
                        _isLoading.value = false
                        _messageBarState.value = MessageBarState(message = response.message)
                        _sessionCleared.value = true
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _messageBarState.value = MessageBarState(error = null)
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

    fun saveSignedInState(signedInState: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveSignedInState(signedInState)
        }
    }

    fun deleteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val response = repository.deleteUser()
                when (response) {
                    is RequestState.Error -> {
                        _isLoading.value = false
                        _messageBarState.value = MessageBarState(error = response.error)
                    }
                    is RequestState.Success -> {
                        _isLoading.value = false
                        _messageBarState.value = MessageBarState(message = response.message)
                        _sessionCleared.value = true
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _messageBarState.value = MessageBarState(error = e)
            }
        }
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

class EmptyFieldException(
    override val message: String? = "Empty Input Field"
) : Exception()

class NothingToUpdateException(
    override val message: String? = "Nothing to Update."
) : Exception()