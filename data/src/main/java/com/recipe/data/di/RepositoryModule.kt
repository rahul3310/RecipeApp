package com.recipe.data.di

import com.recipe.data.local.LocalDataSource
import com.recipe.data.repository.RemoteRepository
import com.recipe.network.api.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesRecipeRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): RemoteRepository {
        return RemoteRepository(remoteDataSource, localDataSource)
    }
}
