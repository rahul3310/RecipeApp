package com.recipe.data.repository

import com.recipe.data.local.LocalDataSource
import com.recipe.data.mapper.toDomain
import com.recipe.data.mapper.toDomainList
import com.recipe.data.mapper.toEntities
import com.recipe.data.model.Recipe
import com.recipe.data.model.RecipeSearch
import com.recipe.network.api.RemoteDataSource
import com.recipe.common.utils.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.isNotEmpty

@Singleton
class RemoteRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    /**
     * Refresh random recipes from API → save in DB.
     */
    suspend fun fetchRandomRecipes(number: Int = 20) : NetworkResult<Unit> {
        return when (val response = remoteDataSource.getRandomRecipes(number)) {
            is NetworkResult.Success -> {
                val recipesInfo = response.data?.recipes?.map { it.toEntities() } ?: emptyList()

                if (recipesInfo.isNotEmpty()) {
                    // Extract different entities
                    val recipes = recipesInfo.map { it.recipe }
                    val ingredients = recipesInfo.flatMap { it.ingredients }
                    val nutrients = recipesInfo.flatMap { it.nutrients }
                    val instructions = recipesInfo.flatMap { it.instructions }
                    val equipment = recipesInfo.flatMap { it.equipment }

                    // Save them into DB
                    localDataSource.insertRecipe(recipes)
                    localDataSource.insertIngredients(ingredients)
                    localDataSource.insertNutrients(nutrients)
                    localDataSource.insertInstructions(instructions)
                    localDataSource.insertEquipments(equipment)
                }

                NetworkResult.Success(Unit)
            }

            is NetworkResult.Error -> {
                NetworkResult.Error(response.errorMessage, response.errorCode)
            }

           else -> NetworkResult.Loading(Unit)
        }
    }


    /**
     * Search recipes from API (optional: also cache).
     */
    suspend fun searchRecipes(
        query: String,
        number: Int = 10,
        offset: Int = 0
    ): NetworkResult<List<RecipeSearch>> {
        return when (val response = remoteDataSource.searchRecipes(query, number, offset)) {
            is NetworkResult.Success -> {
                val recipeSearch = response.data?.results?.map {
                    it.toDomain()
                }
                NetworkResult.Success(recipeSearch)
            }

            is NetworkResult.Error -> {
                NetworkResult.Error(response.errorMessage, response.errorCode)
            }

            else -> NetworkResult.Loading()
        }
    }

    suspend fun getSimilarRecipes(
        recipeId: Int
    ): NetworkResult<List<Recipe>> {
        return when (val response = remoteDataSource.getSimilarRecipes(recipeId)) {
            is NetworkResult.Success -> {
                val recipeSearch = response.data?.toDomainList()
                NetworkResult.Success(recipeSearch)
            }

            is NetworkResult.Error -> {
                NetworkResult.Error(response.errorMessage, response.errorCode)
            }

            else -> NetworkResult.Loading()
        }
    }
}
