package info.prorabka.abik.android.presentation.screens.teams

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import info.prorabka.abik.android.domain.model.TeamBalance
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalancesScreen(
    viewModel: BalancesViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Балансы участников") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = uiState) {
                is BalancesUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is BalancesUiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadBalances() }) {
                            Text("Повторить")
                        }
                    }
                }
                is BalancesUiState.Success -> {
                    if (state.balances.isEmpty()) {
                        Text(
                            text = "В команде пока нет участников",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.balances) { balance ->
                                BalanceCard(balance)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BalanceCard(balance: TeamBalance) {
    // Форматируем копейки в рубли (например: 150000 -> 1500.00 ₽)
    val formatter = NumberFormat.getCurrencyInstance(Locale("ru", "RU"))
    val amountRub = balance.amountKopecks / 100.0
    val formattedAmount = formatter.format(amountRub)

    // Цвет: Красный для долга (минус), Зеленый для переплаты (плюс)
    val isDebt = balance.amountKopecks < 0
    val amountColor = if (isDebt) {
        MaterialTheme.colorScheme.error
    } else if (balance.amountKopecks > 0) {
        Color(0xFF2E7D32) // Темно-зеленый
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = balance.fullName,
                style = MaterialTheme.typography.bodyLarge
            )

            // Красивая плашка с суммой
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(amountColor.copy(alpha = 0.1f))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = if (balance.amountKopecks > 0) "+$formattedAmount" else formattedAmount,
                    color = amountColor,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}