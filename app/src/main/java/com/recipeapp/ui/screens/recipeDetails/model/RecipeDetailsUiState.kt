package com.recipeapp.ui.screens.recipeDetails.model

import androidx.compose.runtime.Immutable
import com.recipe.data.model.RecipeWithDetails

@Immutable
data class RecipeDetailsUiState(
    val recipeDetails: RecipeWithDetails? = null,
    val isMarkedFav : Boolean = false,
    val showBottomSheet : Boolean = false
)