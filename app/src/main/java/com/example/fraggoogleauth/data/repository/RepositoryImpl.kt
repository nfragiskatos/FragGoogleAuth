package com.example.fraggoogleauth.data.repository

import com.example.fraggoogleauth.data.mapper.toUser
import com.example.fraggoogleauth.data.mapper.toUserUpdateDto
import com.example.fraggoogleauth.data.remote.KtorApi
import com.example.fraggoogleauth.data.remote.dto.ApiRequestDto
import com.example.fraggoogleauth.domain.model.User
import com.example.fraggoogleauth.domain.model.UserUpdate
import com.example.fraggoogleauth.domain.repository.DataStoreOperations
import com.example.fraggoogleauth.domain.repository.Repository
import com.example.fraggoogleauth.util.RequestState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dataStoreOperations: DataStoreOperations,
    private val ktorApi: KtorApi
) :
    Repository {
    override suspend fun saveSignedInState(signedIn: Boolean) {
        dataStoreOperations.saveSignedInState(signedIn)
    }

    override fun readSignedInState(): Flow<Boolean> {
        return dataStoreOperations.readSignedInState()
    }

    override suspend fun verifyTokenOnBackend(tokenId: String): RequestState<Boolean> {
        return try {
            val response = ktorApi.verifyTokenOnBackend(ApiRequestDto(tokenId = tokenId))
            RequestState.Success(
                data = response.success,
                message = response.message
            )
        } catch (e: Exception) {
            RequestState.Error(error = e)
        }
    }

    override suspend fun getUserInfo(): RequestState<User> {
        return try {
            val response = ktorApi.getUserInfo()
            if (response.user != null) {
                RequestState.Success(
                    data = response.user.toUser(),
                    message = response.message
                )
            } else {
                RequestState.Error(Exception("User not found."))
            }

        } catch (e: Exception) {
            RequestState.Error(error = e)
        }
    }

    override suspend fun updateUser(userUpdate: UserUpdate): RequestState<Boolean> {
        return try {
            val response = ktorApi.updateUser(userUpdate.toUserUpdateDto())
            RequestState.Success(
                data = response.success,
                message = response.message
            )
        } catch (e: Exception) {
            RequestState.Error(error = e)
        }
    }

    override suspend fun deleteUser(): RequestState<Boolean> {
        return try {
            val response = ktorApi.deleteUser()
            RequestState.Success(
                data = response.success,
                message = response.message
            )
        } catch (e: Exception) {
            RequestState.Error(error = e)
        }
    }

    override suspend fun clearSession(): RequestState<Boolean> {
        return try {
            val response = ktorApi.clearSession()
            RequestState.Success(
                data = response.success,
                message = response.message
            )
        } catch (e: Exception) {
            RequestState.Error(error = e)
        }
    }
}