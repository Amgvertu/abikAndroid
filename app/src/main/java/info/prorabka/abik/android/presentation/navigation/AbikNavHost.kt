package info.prorabka.abik.android.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import info.prorabka.abik.android.domain.repository.AuthRepository
import info.prorabka.abik.android.presentation.components.AbikBottomNavBar
import info.prorabka.abik.android.presentation.components.AbikTopAppBar
import info.prorabka.abik.android.presentation.screens.auth.*
import info.prorabka.abik.android.presentation.screens.finance.FinanceScreen
import info.prorabka.abik.android.presentation.screens.profile.ProfileScreen
import info.prorabka.abik.android.presentation.screens.teams.BalancesScreen
import info.prorabka.abik.android.presentation.screens.teams.CreateTeamScreen

@Composable
fun AbikNavHost(authRepository: AuthRepository) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val startDestination = if (authRepository.isLoggedIn()) {
        Screen.Finance.route
    } else {
        Screen.Login.route
    }

    // Показываем тулбар и нижнее меню только на главных экранах
    val isMainScreen = currentRoute in listOf(
        Screen.Finance.route, Screen.Schedule.route, Screen.Team.route, Screen.Profile.route
    )

    Scaffold(
        topBar = {
            if (isMainScreen) {
                AbikTopAppBar(
                    teamName = "ХК Союз", // TODO: Брать из выбранной команды
                    balanceText = "+1 500 ₽", // TODO: Брать из API
                    onTeamClick = { navController.navigate(Screen.TeamSelect.route) },
                    onNotificationsClick = { /* TODO: Открыть уведомления */ },
                    onMenuClick = { /* TODO: Доп. меню */ }
                )
            }
        },
        bottomBar = {
            if (isMainScreen) {
                AbikBottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            // --- АВТОРИЗАЦИЯ ---
            composable(Screen.Login.route) {
                val authViewModel: AuthViewModel = hiltViewModel()
                LoginScreen(
                    viewModel = authViewModel,
                    onLoginSuccess = { navController.navigate(Screen.Finance.route) { popUpTo(0) } },
                    onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                    onNavigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) }
                )
            }
            composable(Screen.Register.route) { RegisterScreen(onBack = { navController.popBackStack() }) }
            composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(onBack = { navController.popBackStack() }) }

            // --- ГЛАВНЫЕ ВКЛАДКИ ---
            composable(Screen.Finance.route) {
                FinanceScreen(
                    onNavigateToCharges = { navController.navigate(Screen.Charges.route) },
                    onNavigateToPayments = { navController.navigate(Screen.Payments.route) },
                    onNavigateToBalances = { navController.navigate(Screen.Balances.route) }
                )
            }

            composable(Screen.CreateTeam.route) {
                CreateTeamScreen(
                    onBack = { navController.popBackStack() },
                    onSuccess = {
                        // После успеха идем на Финансы (или обновляем дашборд)
                        navController.navigate(Screen.Finance.route) {
                            popUpTo(Screen.CreateTeam.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Schedule.route) {
                // Простая заглушка
                androidx.compose.material3.Text("Расписание в разработке")
            }

            composable(Screen.Team.route) {
                // Простая заглушка
                androidx.compose.material3.Text("Управление командой в разработке")
            }

            composable(Screen.Profile.route) {
                val authViewModel: AuthViewModel = hiltViewModel()
                ProfileScreen(
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) { popUpTo(0) }
                    }
                )
            }

            // --- ВЛОЖЕННЫЕ ЭКРАНЫ (Вызываются из Финансов) ---
            composable(Screen.Charges.route) {
                // Пока заглушка, сделаем в следующий раз
                androidx.compose.material3.Text("Начисления (в разработке)")
            }
            composable(Screen.Payments.route) {
                androidx.compose.material3.Text("Платежи (в разработке)")
            }
            composable(Screen.Balances.route) {
                BalancesScreen(onBack = { navController.popBackStack() })
            }

            composable(Screen.TeamSelect.route) {
                // Экран выбора команды (в разработке)
                androidx.compose.material3.Text("Выбор команды")
            }
        }
    }
}