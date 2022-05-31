package com.example.fraggoogleauth.domain.repository

import com.example.fraggoogleauth.domain.model.User
import com.example.fraggoogleauth.domain.model.UserUpdate
import com.example.fraggoogleauth.util.RequestState
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun saveSignedInState(signedIn: Boolean)
    fun readSignedInState(): Flow<Boolean>

    suspend fun verifyTokenOnBackend(tokenId: String): RequestState<Boolean>
    suspend fun getUserInfo(): RequestState<User>
    suspend fun updateUser(userUpdate: UserUpdate): RequestState<Boolean>
    suspend fun deleteUser(): RequestState<Boolean>
    suspend fun clearSession(): RequestState<Boolean>
}