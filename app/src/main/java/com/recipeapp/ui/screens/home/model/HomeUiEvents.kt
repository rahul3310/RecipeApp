package com.recipeapp.ui.screens.home.model

import com.recipe.data.entity.RecipeEntity
import com.recipe.data.model.Recipe

sealed class HomeUiEvents {
    data class NavigateToRecipeDetails(val route: String , val recipeId: Int): HomeUiEvents()
    data class OnSearchItemClick(val searchResult: Recipe): HomeUiEvents()
}