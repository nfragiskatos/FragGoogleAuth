package com.example.fraggoogleauth.data.repository

import com.example.fraggoogleauth.domain.repository.DataStoreOperations
import com.example.fraggoogleauth.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val dataStoreOperations: DataStoreOperations) :
    Repository {
    override suspend fun saveSignedInState(signedIn: Boolean) {
        dataStoreOperations.saveSignedInState(signedIn)
    }

    override fun readSignedInState(): Flow<Boolean> {
        return dataStoreOperations.readSignedInState()
    }
}