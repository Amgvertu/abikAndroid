package info.prorabka.abik.android.data.remote.dto

import kotlinx.serialization.Serializable

// ============================================================
// Запросы (Request)
// ============================================================

@Serializable
data class RegisterStartRequest(
    val phone: String,
    val password: String,
    val acceptedPd: Boolean,
    val policyVersion: String
)

@Serializable
data class RegisterConfirmRequest(
    val phone: String,
    val code: String
)

@Serializable
data class LoginRequest(
    val phone: String,
    val password: String
)

@Serializable
data class RefreshRequest(
    val refreshToken: String
)

@Serializable
data class LogoutRequest(
    val refreshToken: String
)

// ============================================================
// Ответы (Response)
// ============================================================

@Serializable
data class RegisterStartResponse(
    val phone: String,
    val message: String
)

@Serializable
data class TokenPairDto(
    val accessToken: String,
    val refreshToken: String,
    val accessExpiresAt: String,
    val refreshExpiresAt: String
)

@Serializable
data class UserResponseDto(
    val id: Long,
    val phone: String,
    val status: String,
    val createdAt: String
)

@Serializable
data class AuthResponseDto(
    val tokens: TokenPairDto,
    val user: UserResponseDto
)