package info.prorabka.abik.android.data.remote.api

import info.prorabka.abik.android.data.remote.dto.*
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("v1/auth/register/start")
    suspend fun registerStart(@Body body: RegisterStartRequest): RegisterStartResponse

    @POST("v1/auth/register/confirm")
    suspend fun registerConfirm(@Body body: RegisterConfirmRequest): AuthResponseDto

    @POST("v1/auth/login")
    suspend fun login(@Body body: LoginRequest): AuthResponseDto

    @POST("v1/auth/refresh")
    suspend fun refresh(@Body body: RefreshRequest): AuthResponseDto

    @POST("v1/auth/logout")
    suspend fun logout(@Body body: LogoutRequest)
}