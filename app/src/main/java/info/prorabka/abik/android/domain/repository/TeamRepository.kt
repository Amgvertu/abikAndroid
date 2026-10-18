package info.prorabka.abik.android.domain.repository

import info.prorabka.abik.android.domain.model.Team
import info.prorabka.abik.android.domain.model.TeamBalance

interface TeamRepository {
    suspend fun getMyTeams(): List<Team>
    suspend fun getBalances(teamId: Long): List<TeamBalance>
}