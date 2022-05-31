package com.example.fraggoogleauth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val name: String,
    val emailAddress: String,
    val profilePhoto: String
)
