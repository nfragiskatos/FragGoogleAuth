package com.example.fraggoogleauth.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ApiResponseDto(
    val success: Boolean,
    val user: UserDto? = null,
    val message: String? = null,
    @Transient val error: Exception? = null
)
