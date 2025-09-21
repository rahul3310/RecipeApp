package com.recipe.data.repository

import com.recipe.data.local.LocalDataSource
import com.recipe.data.mapper.toDomain
import com.recipe.data.model.Recipe
import com.recipe.data.model.RecipeWithDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeDetailsRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) {
    /**
     * Get recipe details from the DB
     * */
    fun getRecipeDetails(recipeId: Int): Flow<RecipeWithDetails?>{
        return localDataSource.getRecipeDetails(recipeId).map { it?.toDomain() }
        }

    /**
     * Toggle favorite flag for a recipe.
     */
    suspend fun toggleFavorite(recipeId: Int, isFavorite: Boolean) {
        localDataSource.updateFavorite(recipeId, isFavorite)
    }

}