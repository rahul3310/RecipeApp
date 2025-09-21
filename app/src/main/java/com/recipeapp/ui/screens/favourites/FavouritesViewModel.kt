package com.recipeapp.ui.screens.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipe.data.repository.FavoriteRepository
import com.recipe.data.repository.HomeRepository
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
   private val favoriteRepository: FavoriteRepository
)  : ViewModel(){


    init {
        fetchFavouriteRecipeList()
    }

    private val _recipeDetailUiState = MutableStateFlow(FavouritesUiState())
    val uiState = _recipeDetailUiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<FavouriteUiEvents>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun fetchFavouriteRecipeList(){
        viewModelScope.launch {
            favoriteRepository.getFavoriteRecipes()
                .collect { favoriteRecipes ->
                    _recipeDetailUiState.value = _recipeDetailUiState.value.copy(
                        favouriteRecipeDetails = favoriteRecipes
                    )
                }
        }
    }

    fun onRecipeClick(route : String , recipeId : Int){
        viewModelScope.launch {
            _uiEvent.emit(FavouriteUiEvents.NavigateToRecipeDetails(route , recipeId))
        }
    }
}