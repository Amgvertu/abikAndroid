package info.prorabka.abik.android.domain.repository

import info.prorabka.abik.android.domain.model.AuthResult

interface AuthRepository {
    suspend fun login(phone: String, password: String): AuthResult
    suspend fun registerStart(phone: String, password: String): String
    suspend fun registerConfirm(phone: String, code: String): AuthResult
    suspend fun logout()
    fun isLoggedIn(): Boolean
}