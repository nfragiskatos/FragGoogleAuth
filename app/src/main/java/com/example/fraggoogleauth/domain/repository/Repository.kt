package com.example.fraggoogleauth.domain.repository

import com.example.fraggoogleauth.domain.model.ApiRequest
import com.example.fraggoogleauth.domain.model.ApiResponse
import com.example.fraggoogleauth.domain.model.UserUpdate
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun saveSignedInState(signedIn: Boolean)
    fun readSignedInState(): Flow<Boolean>

    suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse
    suspend fun getUserInfo(): ApiResponse
    suspend fun updateUser(userUpdate: UserUpdate): ApiResponse
    suspend fun deleteUser(): ApiResponse
    suspend fun clearSession(): ApiResponse
}