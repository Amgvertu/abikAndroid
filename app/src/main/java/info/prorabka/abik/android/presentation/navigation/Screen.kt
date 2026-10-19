package info.prorabka.abik.android.presentation.navigation

sealed class Screen(val route: String) {
    // Авторизация
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object ForgotPassword : Screen("forgot_password")

    // Главные вкладки (Нижнее меню)
    data object Finance : Screen("finance")
    data object Schedule : Screen("schedule")
    data object Team : Screen("team")
    data object Profile : Screen("profile")

    // Экран выбора команды (если нажать на дропдаун в тулбаре)
    data object TeamSelect : Screen("team_select")
    data object CreateTeam : Screen("create_team")

    // Вложенные экраны (внутри вкладки Финансы)
    data object Charges : Screen("charges")
    data object Payments : Screen("payments")
    data object Balances : Screen("balances")
}