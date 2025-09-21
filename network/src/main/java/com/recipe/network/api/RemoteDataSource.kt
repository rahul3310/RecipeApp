package com.recipe.network.api

import com.recipe.common.utils.NetworkResult
import com.recipe.network.modelDto.RecipeInformationDTO
import com.recipe.network.modelDto.RecipeSearchResponseDTO
import com.recipe.network.modelDto.SimilarRecipe
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiServices: ApiServices
) {
    suspend fun getRandomRecipes(number: Int = 20): NetworkResult<RecipeInformationDTO> {
        return safeApiCall { apiServices.getRandomRecipes(number) }
    }

    suspend fun getSimilarRecipes(recipeId: Int, number: Int? = null): NetworkResult<List<SimilarRecipe>> {
        return safeApiCall { apiServices.getSimilarRecipes(recipeId, number) }
    }

    suspend fun searchRecipes(query: String, number: Int = 10, offset: Int = 0): NetworkResult<RecipeSearchResponseDTO> {
        return safeApiCall { apiServices.searchRecipes(query, number, offset) }
    }
}
