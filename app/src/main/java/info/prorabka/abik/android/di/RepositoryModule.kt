package info.prorabka.abik.android.di

import info.prorabka.abik.android.data.repository.AuthRepositoryImpl
import info.prorabka.abik.android.domain.repository.AuthRepository
import info.prorabka.abik.android.data.repository.TeamRepositoryImpl
import info.prorabka.abik.android.domain.repository.TeamRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTeamRepository(impl: TeamRepositoryImpl): TeamRepository
}