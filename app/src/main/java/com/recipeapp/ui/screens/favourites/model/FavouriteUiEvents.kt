package com.recipeapp.ui.screens.favourites.model

sealed class FavouriteUiEvents {
    data class NavigateToRecipeDetails(val route: String , val recipeId: Int): FavouriteUiEvents()
}