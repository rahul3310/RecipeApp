package com.recipeapp.worker

import com.recipe.data.repository.RemoteRepository
import javax.inject.Inject

class WorkManagerHelper @Inject constructor(
    private val recipeRepository: RemoteRepository
) {
    suspend fun loadRecipeData(){
        recipeRepository.fetchRandomRecipes(20)
    }
}