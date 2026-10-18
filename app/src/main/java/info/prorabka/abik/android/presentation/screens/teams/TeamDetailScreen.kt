package info.prorabka.abik.android.presentation.screens.teams

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

// Пока используем стандартные иконки, потом заменим на кастомные
import androidx.compose.material.icons.filled.*

data class TeamModule(
    val title: String,
    val icon: ImageVector,
    val routeAction: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailScreen(
    teamId: Long,
    teamName: String = "Команда #$teamId", // В будущем будем брать из API
    onBack: () -> Unit,
    onNavigateToCharges: () -> Unit,
    onNavigateToPayments: () -> Unit,
    onNavigateToBalances: () -> Unit
) {
    // Список модулей (пока с заглушками)
    val modules = listOf(
        TeamModule("Начисления", Icons.Default.Money) { onNavigateToCharges() },
        TeamModule("Платежи", Icons.Default.Payment) { onNavigateToPayments() },
        TeamModule("Баланс", Icons.Default.AccountBalanceWallet) { onNavigateToBalances() },
        TeamModule("Расписание", Icons.Default.CalendarMonth) { /* TODO */ },
        TeamModule("Абонемент", Icons.Default.CardMembership) { /* TODO */ },
        TeamModule("Голосовалка", Icons.Default.HowToVote) { /* TODO */ }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(teamName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Управление командой",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(modules) { module ->
                    ModuleCard(module)
                }
            }
        }
    }
}

@Composable
fun ModuleCard(module: TeamModule) {
    Card(
        onClick = module.routeAction,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f), // Квадратные карточки
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = module.icon,
                contentDescription = module.title,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = module.title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}