package com.example.fraggoogleauth.data.mapper

import com.example.fraggoogleauth.data.remote.dto.UserDto
import com.example.fraggoogleauth.domain.model.User

fun UserDto.toUser(): User {
    return User(
        id = id,
        name = name,
        emailAddress = emailAddress,
        profilePhoto = profilePhoto
    )
}