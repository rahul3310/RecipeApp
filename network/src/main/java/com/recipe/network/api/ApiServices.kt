package com.recipe.network.api

import com.recipe.network.modelDto.RecipeSearchResponseDTO
import com.recipe.network.modelDto.RecipeInformationDTO
import com.recipe.network.modelDto.SimilarRecipe
import com.recipe.network.utils.BASE_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

 interface ApiServices {
    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") number: Int = 20
    ): Response<RecipeInformationDTO>

    @GET("recipes/{id}/similar")
    suspend fun getSimilarRecipes(
        @Path("id") recipeId: Int,
        @Query("number") number: Int? = null
    ): Response<List<SimilarRecipe>>

    @GET(BASE_URL+"recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("number") number: Int? = 10,
        @Query("offset") offset: Int? = 0
    ): Response<RecipeSearchResponseDTO>
}