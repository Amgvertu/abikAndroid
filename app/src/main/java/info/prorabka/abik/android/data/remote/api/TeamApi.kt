package info.prorabka.abik.android.data.remote.api

import info.prorabka.abik.android.data.remote.dto.BalanceResponseDto
import info.prorabka.abik.android.data.remote.dto.CreateTeamRequest
import info.prorabka.abik.android.data.remote.dto.TeamDetailResponseDto
import info.prorabka.abik.android.data.remote.dto.TeamResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TeamApi {
    @GET("v1/teams/my")
    suspend fun getMyTeams(): List<TeamResponseDto>

    @GET("v1/teams/{teamId}/balances")
    suspend fun getBalances(@Path("teamId") teamId: Long): List<BalanceResponseDto>

    @POST("v1/teams")
    suspend fun createTeam(@Body request: CreateTeamRequest): TeamDetailResponseDto
}