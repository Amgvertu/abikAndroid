package info.prorabka.abik.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import info.prorabka.abik.android.domain.repository.AuthRepository
import info.prorabka.abik.android.presentation.navigation.AbikNavHost
import info.prorabka.abik.android.ui.theme.AbikAndroidTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Внедряем репозиторий прямо в Activity, чтобы передать его в NavHost
    // для проверки авторизации при старте
    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AbikAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AbikNavHost(authRepository = authRepository)
                }
            }
        }
    }
}