package com.recipeapp.ui.screens.recipeDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipe.data.repository.RecipeDetailsRepository
import com.recipeapp.MainApplication
import com.recipeapp.ui.screens.recipeDetails.model.EnumTime
import com.recipeapp.ui.screens.recipeDetails.model.RecipeDetailsEvents
import com.recipeapp.ui.screens.recipeDetails.model.RecipeDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsScreenViewModel @Inject constructor(
    private val recipeDetailsRepository: RecipeDetailsRepository
) : ViewModel() {

    private val _recipeDetailUiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState = _recipeDetailUiState.asStateFlow()


    fun onEvent(event: RecipeDetailsEvents) {
        when (event) {
            RecipeDetailsEvents.AddReminderClick -> {
                showBottomSheet(true)
            }

            is RecipeDetailsEvents.LoadRecipes -> {
                loadRecipe(event.recipeId)
            }

            is RecipeDetailsEvents.OnTimeSelected -> {
                showBottomSheet(false)
                setNotifyReminder(
                    recipeName = uiState.value.recipeDetails?.recipe?.title ?: "",
                    event.time
                )
            }

            RecipeDetailsEvents.ToggleFavorite -> {
                showBottomSheet(false)
                onFavoriteClick()
            }
            RecipeDetailsEvents.DismissBottomSheet -> {
                showBottomSheet(false)
            }
        }
    }

    private fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            recipeDetailsRepository.getRecipeDetails(recipeId)
                .collect { recipeWithDetails ->
                    recipeWithDetails?.let { recipe ->
                        _recipeDetailUiState.value = _recipeDetailUiState.value.copy(
                            recipeDetails = recipe,
                            isMarkedFav = recipe.recipe.isFavorite
                        )
                    }
                }
        }
    }

    private fun onFavoriteClick() {
        _recipeDetailUiState.update { state ->
            state.copy(isMarkedFav = !uiState.value.isMarkedFav)

        }
        viewModelScope.launch(Dispatchers.IO) {
            recipeDetailsRepository.toggleFavorite(
                _recipeDetailUiState.value.recipeDetails?.recipe?.id!!,
                _recipeDetailUiState.value.isMarkedFav
            )
        }
    }

    private fun showBottomSheet(isVisible: Boolean) {
        _recipeDetailUiState.update { detailsUiState ->
            detailsUiState.copy(showBottomSheet = isVisible)
        }
    }

    private fun setNotifyReminder(recipeName: String, enumTime: EnumTime) {
        MainApplication.instance.setNotifyReminder(recipeName, enumTime)
    }


}