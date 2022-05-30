package com.example.fraggoogleauth.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.fraggoogleauth.data.remote.KtorApi
import com.example.fraggoogleauth.data.repository.DataStoreOperationsImpl
import com.example.fraggoogleauth.data.repository.RepositoryImpl
import com.example.fraggoogleauth.domain.repository.DataStoreOperations
import com.example.fraggoogleauth.domain.repository.Repository
import com.example.fraggoogleauth.util.Constants.PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesDataStorePreferences(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(PREFERENCES_NAME) }
        )
    }

    @Provides
    @Singleton
    fun providesDataStoreOperations(dataStore: DataStore<Preferences>): DataStoreOperations {
        return DataStoreOperationsImpl(dataStore)
    }

    @Provides
    @Singleton
    fun providesRepository(dataStoreOperations: DataStoreOperations, ktorApi: KtorApi): Repository {
        return RepositoryImpl(dataStoreOperations, ktorApi)
    }
}