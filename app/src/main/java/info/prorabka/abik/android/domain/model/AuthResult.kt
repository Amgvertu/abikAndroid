package info.prorabka.abik.android.domain.model

data class AuthResult(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)