package com.recipeapp.ui.screens.home.model

import androidx.compose.runtime.Immutable
import com.recipe.data.entity.RecipeEntity
import com.recipe.data.model.Recipe
import com.recipe.data.model.RecipeWithDetails

@Immutable
data class HomeUiState(
    val searchQuery: String = "",
    val searchResult: List<Recipe> = emptyList(),
    val popularRecipes: List<Recipe> = emptyList(),
    val allRecipes: List<Recipe> = emptyList(),
    val recipeDetails: RecipeWithDetails? = null,
    val bottomSheetState: BottomSheetState? = null
)

sealed class BottomSheetState() {
    data class SearchBottomSheet(val recipe: Recipe) : BottomSheetState()
    data class IngredientBottomSheet(val recipe: Recipe) : BottomSheetState()
    data class SimilarRecipeBottomSheet(val recipe: Recipe) : BottomSheetState()
}