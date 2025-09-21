package com.recipe.data.repository

import com.recipe.data.local.LocalDataSource
import com.recipe.data.mapper.toDomainRecipeList
import com.recipe.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val localDataSource: LocalDataSource
){
    /**
     * Get all recipes from local DB (cached).
     */
    fun getAllRecipes(): Flow<List<Recipe>> =
        localDataSource.getAllRecipes().map { it.toDomainRecipeList() }

    /**
     * Toggle favorite flag for a recipe.
     */
    suspend fun toggleFavorite(recipeId: Int, isFavorite: Boolean) {
        localDataSource.updateFavorite(recipeId, isFavorite)
    }

    /**
     * Search the recipe by Recipe name and ingredient name
     * */

    fun searchRecipes(query: String) : Flow<List<Recipe>>{
       return localDataSource.searchRecipes(query).map { it.toDomainRecipeList() }
    }
}