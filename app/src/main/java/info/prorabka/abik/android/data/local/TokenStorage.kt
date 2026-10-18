package info.prorabka.abik.android.data.local

/**
 * Абстракция для хранения JWT-токенов.
 */
interface TokenStorage {
    fun saveTokens(accessToken: String, refreshToken: String)
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun clear()
}