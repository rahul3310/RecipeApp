package com.recipeapp.ui.screens.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipe.data.repository.FavoriteRepository
import com.recipe.data.repository.HomeRepository
import com.recipe.data.repository.RecipeDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.recipeapp.ui.screens.favourites.model.FavouriteUiEvents
import com.recipeapp.ui.screens.favourites.model.FavouritesUiState
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val recipeDetailsRepository: RecipeDetailsRepository,
) : ViewModel() {


    init {
        fetchFavouriteRecipeList()
    }

    private val _favoriteUiState = MutableStateFlow(FavouritesUiState())
    val uiState = _favoriteUiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<FavouriteUiEvents>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun fetchFavouriteRecipeList() {
        viewModelScope.launch {
            favoriteRepository.getFavoriteRecipes()
                .collect { favoriteRecipes ->
                    _favoriteUiState.value = _favoriteUiState.value.copy(
                        favouriteRecipeDetails = favoriteRecipes
                    )
                }
        }
    }

    fun onRecipeClick(route: String, recipeId: Int) {
        viewModelScope.launch {
            _uiEvent.emit(FavouriteUiEvents.NavigateToRecipeDetails(route, recipeId))
        }
    }

    fun removeFavorites(recipeId: Int) {
        val currentState = _favoriteUiState.value
        val updatedList = currentState.favouriteRecipeDetails.filterNot { it.id == recipeId }

        _favoriteUiState.value = currentState.copy(
            favouriteRecipeDetails = updatedList
        )
        viewModelScope.launch {
            recipeDetailsRepository.toggleFavorite(recipeId, false)
        }
    }

}