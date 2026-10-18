package info.prorabka.abik.android.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import info.prorabka.abik.android.domain.repository.AuthRepository
import info.prorabka.abik.android.presentation.screens.PlaceholderScreen
import info.prorabka.abik.android.presentation.screens.auth.AuthViewModel
import info.prorabka.abik.android.presentation.screens.auth.ForgotPasswordScreen
import info.prorabka.abik.android.presentation.screens.auth.LoginScreen
import info.prorabka.abik.android.presentation.screens.auth.RegisterScreen
import info.prorabka.abik.android.presentation.screens.notifications.NotificationsScreen
import info.prorabka.abik.android.presentation.screens.profile.ProfileScreen
import info.prorabka.abik.android.presentation.screens.teams.*

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun AbikNavHost(authRepository: AuthRepository) {
    val navController = rememberNavController()

    // Отслеживаем текущий экран для нижнего меню
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val startDestination = if (authRepository.isLoggedIn()) {
        Screen.TeamsList.route
    } else {
        Screen.Login.route
    }

    // Список вкладок для нижнего меню
    val bottomNavItems = listOf(
        BottomNavItem(Screen.TeamsList.route, "Команды", Icons.Default.Groups),
        BottomNavItem(Screen.Notifications.route, "Уведомления", Icons.Default.Notifications),
        BottomNavItem(Screen.Profile.route, "Профиль", Icons.Default.Person)
    )

    // Показываем нижнее меню только на главных экранах
    val showBottomBar = currentRoute in bottomNavItems.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(item.title) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            // ==========================================
            // АВТОРИЗАЦИЯ
            // ==========================================
            composable(Screen.Login.route) {
                val authViewModel: AuthViewModel = hiltViewModel()
                LoginScreen(
                    viewModel = authViewModel,
                    onLoginSuccess = {
                        navController.navigate(Screen.TeamsList.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                    onNavigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) }
                )
            }

            composable(Screen.Register.route) {
                RegisterScreen(onBack = { navController.popBackStack() })
            }

            composable(Screen.ForgotPassword.route) {
                ForgotPasswordScreen(onBack = { navController.popBackStack() })
            }

            // ==========================================
            // ГЛАВНЫЕ ВКЛАДКИ
            // ==========================================
            composable(Screen.TeamsList.route) {
                val teamsViewModel: TeamsViewModel = hiltViewModel()
                TeamsScreen(
                    viewModel = teamsViewModel,
                    onTeamClick = { teamId ->
                        navController.navigate(Screen.TeamDetail.createRoute(teamId))
                    }
                )
            }

            composable(Screen.Notifications.route) {
                NotificationsScreen()
            }

            composable(Screen.Profile.route) {
                val authViewModel: AuthViewModel = hiltViewModel()
                ProfileScreen(
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true } // Очищаем весь стек
                        }
                    }
                )
            }

            // ==========================================
            // ВНУТРЕННИЕ ЭКРАНЫ КОМАНДЫ
            // ==========================================
            composable(
                route = Screen.TeamDetail.route,
                arguments = listOf(navArgument("teamId") { type = NavType.LongType })
            ) { backStackEntry ->
                val teamId = backStackEntry.arguments?.getLong("teamId") ?: 0L
                TeamDetailScreen(
                    teamId = teamId,
                    onBack = { navController.popBackStack() },
                    onNavigateToCharges = { navController.navigate(Screen.TeamCharges.createRoute(teamId)) },
                    onNavigateToPayments = { navController.navigate(Screen.TeamPayments.createRoute(teamId)) },
                    onNavigateToBalances = { navController.navigate(Screen.TeamBalances.createRoute(teamId)) }
                )
            }

            composable(
                route = Screen.TeamCharges.route,
                arguments = listOf(navArgument("teamId") { type = NavType.LongType })
            ) { backStackEntry ->
                val teamId = backStackEntry.arguments?.getLong("teamId") ?: 0L
                PlaceholderScreen(title = "Начисления #$teamId", onBack = { navController.popBackStack() })
            }

            composable(
                route = Screen.TeamPayments.route,
                arguments = listOf(navArgument("teamId") { type = NavType.LongType })
            ) { backStackEntry ->
                val teamId = backStackEntry.arguments?.getLong("teamId") ?: 0L
                PlaceholderScreen(title = "Платежи #$teamId", onBack = { navController.popBackStack() })
            }

            composable(
                route = Screen.TeamBalances.route,
                arguments = listOf(navArgument("teamId") { type = NavType.LongType })
            ) {
                BalancesScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}