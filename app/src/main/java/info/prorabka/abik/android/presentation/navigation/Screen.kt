package info.prorabka.abik.android.presentation.navigation

sealed class Screen(val route: String) {
    // Авторизация
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object ForgotPassword : Screen("forgot_password")

    // Главные вкладки
    data object TeamsList : Screen("teams_list")
    data object Profile : Screen("profile")
    data object Notifications : Screen("notifications")

    // Вложенные экраны команды
    data object TeamDetail : Screen("team_detail/{teamId}") {
        fun createRoute(teamId: Long) = "team_detail/$teamId"
    }
    data object TeamCharges : Screen("team_charges/{teamId}") {
        fun createRoute(teamId: Long) = "team_charges/$teamId"
    }
    data object TeamPayments : Screen("team_payments/{teamId}") {
        fun createRoute(teamId: Long) = "team_payments/$teamId"
    }
    data object TeamBalances : Screen("team_balances/{teamId}") {
        fun createRoute(teamId: Long) = "team_balances/$teamId"
    }
}