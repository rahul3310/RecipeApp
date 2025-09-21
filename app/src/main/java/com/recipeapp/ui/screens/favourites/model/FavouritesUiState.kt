package com.recipeapp.ui.screens.favourites.model

import androidx.compose.runtime.Immutable
import com.recipe.data.model.Recipe

@Immutable
data class FavouritesUiState(
    val favouriteRecipeDetails : List<Recipe> = emptyList(),
)