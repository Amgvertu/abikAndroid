package info.prorabka.abik.android.presentation.screens.finance

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.prorabka.abik.android.presentation.navigation.Screen

@Composable
fun ScheduleScreen(
    onNavigateToScheduleTrainings: () -> Unit,
    onNavigateToScheduleMatch: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Финансы команды", style = MaterialTheme.typography.headlineSmall)
        Button(onClick = onNavigateToScheduleTrainings) { Text("Расписание тренировок") }
        Button(onClick = onNavigateToScheduleMatch) { Text("Расписание игр") }
    }
}