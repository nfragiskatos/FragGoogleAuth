package com.example.fraggoogleauth.data.mapper

import com.example.fraggoogleauth.data.remote.dto.UserUpdateDto
import com.example.fraggoogleauth.domain.model.UserUpdate

fun UserUpdate.toUserUpdateDto(): UserUpdateDto {
    return UserUpdateDto(
        firstName = firstName,
        lastName = lastName
    )
}