package info.prorabka.abik.android.data.repository

import info.prorabka.abik.android.data.local.TokenStorage
import info.prorabka.abik.android.data.remote.api.AuthApi
import info.prorabka.abik.android.data.remote.dto.AuthResponseDto
import info.prorabka.abik.android.data.remote.dto.LoginRequest
import info.prorabka.abik.android.data.remote.dto.LogoutRequest
import info.prorabka.abik.android.data.remote.dto.RegisterConfirmRequest
import info.prorabka.abik.android.data.remote.dto.RegisterStartRequest
import info.prorabka.abik.android.domain.model.AuthResult
import info.prorabka.abik.android.domain.model.User
import info.prorabka.abik.android.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override suspend fun login(phone: String, password: String): AuthResult {
        val response = api.login(LoginRequest(phone, password))
        return saveAuthResponse(response)
    }

    override suspend fun registerStart(phone: String, password: String): String {
        val response = api.registerStart(
            RegisterStartRequest(
                phone = phone,
                password = password,
                acceptedPd = true,
                policyVersion = "1.0"
            )
        )
        return response.message
    }

    override suspend fun registerConfirm(phone: String, code: String): AuthResult {
        val response = api.registerConfirm(RegisterConfirmRequest(phone, code))
        return saveAuthResponse(response)
    }

    override suspend fun logout() {
        val refreshToken = tokenStorage.getRefreshToken()
        if (refreshToken != null) {
            try {
                api.logout(LogoutRequest(refreshToken))
            } catch (e: Exception) {
                // Игнорируем ошибку сети при логауте, главное — очистить локально
            }
        }
        tokenStorage.clear()
    }

    override fun isLoggedIn(): Boolean {
        return tokenStorage.getAccessToken() != null
    }

    private fun saveAuthResponse(response: AuthResponseDto): AuthResult {
        // Сохраняем токены
        tokenStorage.saveTokens(
            response.tokens.accessToken,
            response.tokens.refreshToken
        )

        // Мапим DTO в доменную модель
        return AuthResult(
            user = User(
                id = response.user.id,
                phone = response.user.phone,
                status = response.user.status
            ),
            accessToken = response.tokens.accessToken,
            refreshToken = response.tokens.refreshToken
        )
    }
}