package com.recipeapp.ui.screens.favourites

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.recipeapp.navigation.Screen
import com.recipeapp.ui.screens.favourites.model.FavouriteUiEvents
import com.recipeapp.ui.theme.BackgroundColorPrimary
import com.recipeapp.ui.theme.BackgroundColorSecondary
import com.recipeapp.ui.utils.RecipeCard
import com.recipeapp.ui_component.LazyColumnCustom
import com.recipeapp.ui_component.TextHeadline
import com.recipeapp.ui_component.TextMedium


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    navController: NavController,
    viewModel: FavouritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEvent.collect { events ->
                when (events) {
                    is FavouriteUiEvents.NavigateToRecipeDetails -> {
                        navController.navigate(Screen.RecipeDetails.createRoute(events.recipeId))
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextHeadline(
                        text = "Favourite Recipes"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundColorSecondary,
                )
            )
        },
        containerColor = BackgroundColorPrimary
    ) { paddingValues ->

        LazyColumnCustom(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            items = uiState.favouriteRecipeDetails,
            emptyListContent = {
                TextMedium(
                    text = "You have not selected any favorite recipe yet.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            },
            content = { index, recipe ->
                Spacer(modifier = Modifier.height(16.dp))
                RecipeCard(
                    recipe,
                    onRecipeClick = {
                        viewModel.onRecipeClick(Screen.RecipeDetails.route, recipe.id)
                    }
                )
            }
        )
    }
}