package com.recipe.data.repository

import com.recipe.data.local.LocalDataSource
import com.recipe.data.mapper.toDomainRecipeList
import com.recipe.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) {
    /**
     * Get only favorite recipes from DB.
     */
    fun getFavoriteRecipes(): Flow<List<Recipe>> =
        localDataSource.getFavoriteRecipes().map { it.toDomainRecipeList() }

}