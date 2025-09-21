package com.recipeapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipe.data.entity.RecipeEntity
import com.recipe.data.model.Recipe
import com.recipe.data.repository.HomeRepository
import com.recipe.data.repository.RecipeDetailsRepository
import com.recipeapp.ui.screens.home.model.BottomSheetState
import com.recipeapp.ui.screens.home.model.HomeUiEvents
import com.recipeapp.ui.screens.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val recipeDetailsRepository: RecipeDetailsRepository
) : ViewModel() {


    init {
        // Fetch from API when ViewModel is created
        observeLocalRecipes()
    }

    private fun observeSearch() {
        viewModelScope.launch {
            _homeUiState
                .map { it.searchQuery }
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    when {
                        query.isBlank() -> {
                            flowOf(emptyList())
                        }

                        else -> {
                            homeRepository.searchRecipes(query)
                        }
                    }
                }
                .collect { results ->

                    _homeUiState.update { current ->
                        current.copy(searchResult = results)
                    }
                }
        }
    }


    private val _homeUiState = MutableStateFlow(HomeUiState())
    val uiState = _homeUiState.asStateFlow()
    private val _uiEvent = MutableSharedFlow<HomeUiEvents>()
    val uiEvent = _uiEvent.asSharedFlow()


    fun onSearchQueryChanged(query: String) {
        _homeUiState.update { uiState ->
            uiState.copy(searchQuery = query)
        }
        observeSearch()

    }

    fun onRecipeClick(route: String, recipeId: Int) {
        viewModelScope.launch {
            _uiEvent.emit(HomeUiEvents.NavigateToRecipeDetails(route, recipeId))
        }
    }

    fun onSearchItemClick(recipe: Recipe) {
        viewModelScope.launch {
            _uiEvent.emit(HomeUiEvents.OnSearchItemClick(recipe))
        }
        getRecipeDetails(recipe.id)
    }

    fun updateBottomSheet(newState: BottomSheetState?) {
        _homeUiState.update { uiState ->
            uiState.copy(bottomSheetState = newState)
        }
    }


    private fun observeLocalRecipes() {
        viewModelScope.launch {
            homeRepository.getAllRecipes().collect { recipes ->
                _homeUiState.value = _homeUiState.value.copy(
                    allRecipes = recipes,
                    popularRecipes = recipes
                )
            }
        }
    }

    fun onFavoriteClick(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.toggleFavorite(recipe.id, !recipe.isFavorite)
        }
    }

    private fun getRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            recipeDetailsRepository.getRecipeDetails(recipeId).collect { recipeWithDetails ->
                _homeUiState.update { uiState ->
                    uiState.copy(recipeDetails = recipeWithDetails)
                }
            }
        }

    }

}