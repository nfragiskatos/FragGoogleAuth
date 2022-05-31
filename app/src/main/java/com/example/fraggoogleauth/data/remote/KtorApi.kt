package com.example.fraggoogleauth.data.remote

import com.example.fraggoogleauth.data.remote.dto.ApiRequestDto
import com.example.fraggoogleauth.data.remote.dto.ApiResponseDto
import com.example.fraggoogleauth.data.remote.dto.UserUpdateDto
import retrofit2.http.*

interface KtorApi {

    @POST("/token_verification")
    suspend fun verifyTokenOnBackend(
        @Body request: ApiRequestDto
    ): ApiResponseDto

    @GET("/get_user")
    suspend fun getUserInfo(): ApiResponseDto

    @PUT("/update_user")
    suspend fun updateUser(@Body userUpdate: UserUpdateDto): ApiResponseDto

    @DELETE("/delete_user")
    suspend fun deleteUser(): ApiResponseDto

    @GET("/sign_out")
    suspend fun clearSession(): ApiResponseDto
}