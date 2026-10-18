package info.prorabka.abik.android.data.repository

import info.prorabka.abik.android.data.remote.api.TeamApi
import info.prorabka.abik.android.domain.model.Team
import info.prorabka.abik.android.domain.model.TeamBalance
import info.prorabka.abik.android.domain.repository.TeamRepository
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val api: TeamApi
) : TeamRepository {

    override suspend fun getMyTeams(): List<Team> {
        return api.getMyTeams().map { dto ->
            Team(
                id = dto.id,
                name = dto.name,
                cityName = dto.cityName,
                logoUrl = dto.logoUrl
            )
        }
    }

    override suspend fun getBalances(teamId: Long): List<TeamBalance> {
        return api.getBalances(teamId).map { dto ->
            val name = listOfNotNull(dto.userLastName, dto.userFirstName)
                .joinToString(" ")
                .ifBlank { "Пользователь #${dto.userId}" }

            TeamBalance(
                userId = dto.userId,
                fullName = name,
                amountKopecks = dto.amountKopecks
            )
        }
    }
}