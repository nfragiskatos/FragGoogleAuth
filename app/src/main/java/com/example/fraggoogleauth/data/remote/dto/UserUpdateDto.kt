package com.example.fraggoogleauth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateDto(
    val firstName: String,
    val lastName: String
)
