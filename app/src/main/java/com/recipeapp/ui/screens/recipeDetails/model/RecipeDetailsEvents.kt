package com.recipeapp.ui.screens.recipeDetails.model

sealed class RecipeDetailsEvents {
    data class LoadRecipes(val recipeId: Int) : RecipeDetailsEvents()
    data object AddReminderClick : RecipeDetailsEvents()
    data class OnTimeSelected(val time: EnumTime) : RecipeDetailsEvents()
    data object ToggleFavorite : RecipeDetailsEvents()
    data object DismissBottomSheet : RecipeDetailsEvents()
}