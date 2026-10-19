package info.prorabka.abik.android.presentation.screens.finance

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FinanceScreen(
    onNavigateToCharges: () -> Unit,
    onNavigateToPayments: () -> Unit,
    onNavigateToBalances: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Финансы команды", style = MaterialTheme.typography.headlineSmall)
        Button(onClick = onNavigateToCharges) { Text("Начисления") }
        Button(onClick = onNavigateToPayments) { Text("Платежи") }
        Button(onClick = onNavigateToBalances) { Text("Балансы участников") }
    }
}